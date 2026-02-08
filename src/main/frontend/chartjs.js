import Chart from 'chart.js/auto';

window.createPieChart = function (canvasId, data, options) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    return new Chart(ctx, {
        type: 'pie',
        data: data,
        options: options,
    });
};
