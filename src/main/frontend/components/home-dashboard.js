import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import './home-dashboard.css';

class HomeDashboard extends HTMLElement {
  constructor() {
    super();
    this._data = { dateLabel: '', teams: [] };
    this.page = 0;
    this.pageSize = 3;
    this.map = null;
  }

  set data(value) {
    this._data = value ?? { dateLabel: '', teams: [] };
    this.page = 0;
    this.render();
  }

  get data() { return this._data; }

  connectedCallback() { this.render(); }

  render() {
    const teams = this._data?.teams ?? [];
    const pages = Math.max(1, Math.ceil(teams.length / this.pageSize));
    this.page = Math.min(this.page, pages - 1);
    const visible = teams.slice(this.page * this.pageSize, (this.page + 1) * this.pageSize);

    this.innerHTML = `
      <main class="dashboard">
        <section class="toolbar">
          <div><span class="eyebrow">Agenda operacional</span><h1>Programação diária</h1></div>
          <span class="date-pill">${this.escape(this._data?.dateLabel || '')}</span>
        </section>
        ${teams.length ? `
          <section class="cards" aria-live="polite">
            ${visible.map((team, index) => this.teamCard(team, index)).join('')}
          </section>
          <nav class="pagination" aria-label="Navegação dos grupos">
            <button data-action="previous" ${this.page === 0 ? 'disabled' : ''}>← Anterior</button>
            <span>${this.page + 1} de ${pages}</span>
            <button data-action="next" ${this.page >= pages - 1 ? 'disabled' : ''}>Próximo →</button>
          </nav>` : this.emptyState()}
        <footer>Mapa de programação diária · ${this.escape(this._data?.dateLabel || '')}</footer>
      </main>
      <dialog class="details-dialog"><div class="dialog-body"></div></dialog>`;

    this.bindEvents();
  }

  teamCard(team, index) {
    const appointments = team.appointments ?? [];
    return `<article class="team-card">
      <header><div class="avatar">${this.initials(team.title)}</div><div><h2>${this.escape(team.title)}</h2><p>${appointments.length} compromisso(s)</p></div></header>
      <div class="appointment-list">
        ${appointments.map(item => `<details>
          <summary><span>${this.escape(item.address || 'Endereço não informado')}</span><time>${this.escape(item.time || '--:--')}</time></summary>
          <dl><div><dt>${item.type === 'OS' ? 'Ordem de serviço' : 'Inspeção técnica'}</dt><dd>${this.escape(String(item.number ?? 'N/A'))}</dd></div>
          <div><dt>Cliente</dt><dd>${this.escape(item.customer || 'N/A')}</dd></div>
          <div><dt>Ponto focal</dt><dd>${this.escape(item.contact || 'N/A')}</dd></div>
          <div><dt>Ocorrência</dt><dd>${this.escape(item.description || 'Não informada')}</dd></div></dl>
        </details>`).join('')}
      </div>
      <button class="details-button" data-team-index="${this.page * this.pageSize + index}">Ver detalhes e mapa</button>
    </article>`;
  }

  emptyState() {
    return `<section class="empty"><div>✓</div><h2>Nenhum compromisso para hoje</h2><p>A programação diária está livre.</p></section>`;
  }

  bindEvents() {
    this.querySelector('[data-action="previous"]')?.addEventListener('click', () => { this.page--; this.render(); });
    this.querySelector('[data-action="next"]')?.addEventListener('click', () => { this.page++; this.render(); });
    this.querySelectorAll('[data-team-index]').forEach(button => {
      button.addEventListener('click', () => this.openDialog(Number(button.dataset.teamIndex)));
    });
  }

  openDialog(index) {
    const team = this._data.teams[index];
    const dialog = this.querySelector('.details-dialog');
    const body = dialog.querySelector('.dialog-body');
    body.innerHTML = `<header class="dialog-header"><div><span class="eyebrow">Equipe</span><h2>${this.escape(team.title)}</h2></div><button class="icon-button" data-close aria-label="Fechar">×</button></header>
      <div class="tabs"><button class="active" data-tab="info">Informações</button><button data-tab="map">Mapa</button></div>
      <section data-panel="info" class="dialog-list">${team.appointments.map(item => `<article><strong>${this.escape(item.type)} ${this.escape(String(item.number ?? ''))}</strong><span>${this.escape(item.time || '--:--')} · ${this.escape(item.address || 'Endereço não informado')}</span><p>${this.escape(item.description || 'Sem ocorrência informada')}</p></article>`).join('')}</section>
      <section data-panel="map" class="map-panel" hidden><div class="map"></div><p class="map-message"></p></section>`;
    body.querySelector('[data-close]').addEventListener('click', () => dialog.close());
    body.querySelectorAll('[data-tab]').forEach(tab => tab.addEventListener('click', () => this.changeTab(body, tab.dataset.tab, team)));
    dialog.showModal();
  }

  async changeTab(body, name, team) {
    body.querySelectorAll('[data-tab]').forEach(x => x.classList.toggle('active', x.dataset.tab === name));
    body.querySelectorAll('[data-panel]').forEach(x => x.hidden = x.dataset.panel !== name);
    if (name === 'map') await this.showMap(body, team.appointments);
  }

  async showMap(body, appointments) {
    const mapElement = body.querySelector('.map');
    const message = body.querySelector('.map-message');
    if (this.map) { this.map.remove(); this.map = null; }
    const located = appointments.filter(x => Number.isFinite(x.latitude) && Number.isFinite(x.longitude));
    if (!located.length) { message.textContent = 'Nenhuma coordenada disponível para esta equipe.'; return; }
    this.map = L.map(mapElement);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19, attribution: '© OpenStreetMap contributors' }).addTo(this.map);
    const bounds = [];
    located.forEach(item => { const point = [item.latitude, item.longitude]; bounds.push(point); L.marker(point).addTo(this.map).bindPopup(`<strong>${this.escape(item.type)} ${this.escape(String(item.number ?? ''))}</strong><br>${this.escape(item.address || '')}`); });
    bounds.length === 1 ? this.map.setView(bounds[0], 15) : this.map.fitBounds(bounds, { padding: [30, 30] });
    setTimeout(() => this.map?.invalidateSize(), 50);
  }

  initials(value = '') { return value.split(/\s+/).filter(Boolean).slice(0, 2).map(x => x[0]).join('').toUpperCase(); }
  escape(value) { const div = document.createElement('div'); div.textContent = String(value ?? ''); return div.innerHTML; }
}

customElements.define('home-dashboard', HomeDashboard);
