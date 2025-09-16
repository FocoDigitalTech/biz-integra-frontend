package br.com.onetec.application.views.layouts;

import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GenericGridEditor<T> {

    private final Class<T> type;

    public GenericGridEditor(Class<T> type) {
        this.type = type;
    }

    /**
     * Adiciona um itemClickListener ao grid para abrir o modal genérico
     * @param grid grid alvo
     * @param onSave callback para salvar no banco
     */
    public void bind(Grid<T> grid, Consumer<T> onSave) {
        grid.addItemClickListener(event -> {
            try {
                openEditor(event.getItem(), onSave, grid);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void openEditor(T item, Consumer<T> onSave, Grid<T> grid) throws IllegalAccessException {
        Dialog modal = new Dialog();
        modal.setWidth("600px");
        FormLayout form = new FormLayout();


        // Para cada coluna adicionada no grid
//        for (Grid.Column<T> column : grid.getColumns()) {
//            String header = column.getHeaderText(); // pega o header da coluna


            for (Field fieldTable : type.getDeclaredFields()) {
                var regex = fieldTable.getName().split("_");
                String headerNome = Arrays.stream(regex).findFirst().get();
                TextField tf = new TextField(headerNome);
                if (!fieldTable.getName().contains("data_inclusao") &&
                        !fieldTable.getName().contains("data_alteracao") &&
                        !fieldTable.getName().contains("ativo") &&
                        !fieldTable.getName().contains("data_exclusao")
                        && !fieldTable.getName().contains("id")) {
                    fieldTable.setAccessible(true);
                    Object value = fieldTable.get(item);
                    tf.setValue(value != null ? value.toString() : "");
                    // listener para salvar de volta no objeto
                    tf.addValueChangeListener(e -> {
                        try {
                            fieldTable.set(item, e.getValue());
                        } catch (IllegalAccessException ex) {
                            Notification.show("Erro ao atualizar campo: " + fieldTable.getName());
                        }
                    });
                    form.add(tf);
                }
            }

            Object values = grid.getDataProvider().getId(item);
            // cria campo só para edição visual (não depende do atributo da classe)



        //}

        Button salvar = new Button("Salvar", e -> {
            UtilitySystemConfigService service = new UtilitySystemConfigService();
            try {
                onSave.accept(item); // callback para persistir no banco
                grid.getDataProvider().refreshItem(item); // atualiza grid
                service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
                modal.close();
            } catch (Exception ex) {
                service.notificaErro("Erro ao salvar: " + ex.getMessage());
            }
        });

        Button cancelar = new Button("Cancelar", e -> modal.close());
        salvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        modal.add(form, new HorizontalLayout(salvar, cancelar));
        modal.open();
    }

    // tentativa de achar o campo correspondente
    private Field findMatchingField(Class<?> type, Grid.Column<?> column) {
        String key = column.getKey();
        if (key != null) {
            try {
                return type.getDeclaredField(key);
            } catch (NoSuchFieldException ignored) {}
        }
        return null; // se não achar, não edita
    }
}

