package br.com.onetec.cross.utilities;

import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.application.service.utilservices.ApiEnderecoService;
import br.com.onetec.application.views.main.clientes.modal.CadastroClientesModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.domain.entity.EApiEnderecoResponse;
import br.com.onetec.infra.db.model.SetEstado;
import br.com.onetec.infra.db.model.SetUsuarios;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class UtilitySystemConfigService {

    private EstadoService estadoService ;

    private ApiEnderecoService cepApiService;

    @Autowired
    public void initServices(EstadoService serviceEstado, ApiEnderecoService cepApiService) {
        this.estadoService = serviceEstado;
        this.cepApiService = cepApiService;
    }

    public SetEstado configuraUF(List<SetEstado> estadoList, String uf) {

        return estadoList.stream()
                .filter(estado -> estado.getUf_estado().equalsIgnoreCase(uf))
                .findFirst()
                .orElse(new SetEstado()); // Retorna vazio se não encontrar o estado


    }

    public String removeMascara(String valor) {
        if (valor == null) {
            return null;
        }
        // Remove todos os caracteres não numéricos
        return valor.replaceAll("[^\\d]", "");
    }

    public DatePicker configuraCalendario (DatePicker date){
        DatePicker.DatePickerI18n datePickerI18n = new DatePicker.DatePickerI18n()
                .setMonthNames(Arrays.asList("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"))
                .setWeekdays(Arrays.asList("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"))
                .setWeekdaysShort(Arrays.asList("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"))
                .setFirstDayOfWeek(1)
                .setCancel("Cancelar")
                .setToday("Hoje");

        date.setI18n(datePickerI18n);
        return date;
    }



    public EApiEnderecoResponse buscarCep(TextField cepField) {
        String cep = cepField.getValue().replaceAll("\\D", "");// Exemplo: limpa caracteres não numéricos
        if (cep.length() == 8) { // Verifica se o CEP tem 8 dígitos
            EApiEnderecoResponse response = cepApiService.get(cep);
            if (response != null) {
                    return response;
            } else {
                // Handle case where address is not found
                notificaErro("Endereço não encontrado !");
                return null;
            }
        } else {
            notificaErro("Cep deve conter 8 digitos !");
            // Handle invalid CEP length
            return null;
        }
    }
   public void notificaSucesso (String MESSAGE){
       Notification notification = Notification.show(MESSAGE);
       notification.addClassName("success-notification");
   }

    public void notificaErro (String MESSAGE){
        Notification notification = Notification.show(MESSAGE);
        notification.addClassName("error-notification");
    }


    public TextField configureCNPJTextField(TextField textField) {
        textField.setMaxLength(18); // Limita ao formato "00.000.000/0000-00"
        textField.setPlaceholder("00.000.000/0000-00");

        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener(event -> {
            String value = event.getValue();
            value = value.replaceAll("[^0-9]", ""); // Remove tudo que não for número

            // Aplica a formatação de CNPJ
            if (value.length() > 2 && value.length() <= 5) {
                value = value.substring(0, 2) + "." + value.substring(2);
            } else if (value.length() > 5 && value.length() <= 8) {
                value = value.substring(0, 2) + "." + value.substring(2, 5) + "." + value.substring(5);
            } else if (value.length() > 8 && value.length() <= 12) {
                value = value.substring(0, 2) + "." + value.substring(2, 5) + "." + value.substring(5, 8) + "/" + value.substring(8);
            } else if (value.length() > 12) {
                value = value.substring(0, 2) + "." + value.substring(2, 5) + "." + value.substring(5, 8) + "/" + value.substring(8, 12) + "-" + value.substring(12);
            }

            // Define o valor formatado no campo de texto
            textField.setValue(value);
        });

        return textField;
    }

    public TextField configureCPFField(TextField cpfField) {
        cpfField.setMaxLength(14); // Limita ao formato "000.000.000-00"
        cpfField.setPlaceholder("000.000.000-00");

        cpfField.setValueChangeMode(ValueChangeMode.EAGER);
        cpfField.addValueChangeListener(event -> {
            String value = event.getValue();
            value = value.replaceAll("[^0-9]", ""); // Remove tudo que não for número

            // Aplica a formatação de CPF
            if (value.length() > 3 && value.length() <= 6) {
                value = value.substring(0, 3) + "." + value.substring(3);
            } else if (value.length() > 6 && value.length() <= 9) {
                value = value.substring(0, 3) + "." + value.substring(3, 6) + "." + value.substring(6);
            } else if (value.length() > 9) {
                value = value.substring(0, 3) + "." + value.substring(3, 6) + "." + value.substring(6, 9) + "-" + value.substring(9);
            }

            // Define o valor formatado no campo de texto
            cpfField.setValue(value);
        });

        return cpfField;
    }

    public TextField configureCelularField(TextField celularField) {
        celularField.setMaxLength(15);
        celularField.setPlaceholder("(XX) XXXXX-XXXX");
        celularField.setValueChangeMode(ValueChangeMode.EAGER);
        celularField.addValueChangeListener(event -> {
            String value = event.getValue();
            value = value.replaceAll("[^0-9]", "");
            if (value.length() > 2) {
                value = "(" + value.substring(0, 2) + ") " + value.substring(2);
            }
            if (value.length() > 9) {
                value = value.substring(0, 10) + "-" + value.substring(10);
            }
            celularField.setValue(value);
        });
        return celularField;
    }

    public TextField configureTelefoneResidencialField(TextField telefoneField) {
        telefoneField.setMaxLength(14); // Máximo de caracteres para o formato (XX) XXXX-XXXX
        telefoneField.setPlaceholder("(XX) XXXX-XXXX");
        telefoneField.setValueChangeMode(ValueChangeMode.EAGER); // Atualiza o valor conforme o usuário digita

        telefoneField.addValueChangeListener(event -> {
            String value = event.getValue();
            // Remove qualquer caractere não numérico
            value = value.replaceAll("[^0-9]", "");

            // Adiciona o formato do telefone residencial
            if (value.length() > 2) {
                value = "(" + value.substring(0, 2) + ") " + value.substring(2);
            }
            if (value.length() > 8) {
                value = value.substring(0, 9) + "-" + value.substring(9);
            }

            // Evita que o campo fique alterando para valores incorretos
            if (!event.getOldValue().equals(value)) {
                telefoneField.setValue(value);
            }
        });

        return telefoneField;
    }

    public TextField configureCEPField(TextField cepField) {
        cepField.setMaxLength(9);
        cepField.setPlaceholder("XXXXX-XXX");
        cepField.setValueChangeMode(ValueChangeMode.EAGER);

        // Adiciona o listener para formatação do CEP
        cepField.addValueChangeListener(event -> {
            String value = event.getValue().replaceAll("[^0-9]", ""); // Remove caracteres não numéricos

            if (value.length() > 5) {
                value = value.substring(0, 5) + "-" + value.substring(5);
            }

            // Define o valor formatado no campo
            cepField.setValue(value);
        });

        return cepField;
    }

    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(LOCALE_BR));


    public TextField formataMoedaBrasileira(TextField valor_item) {
        String value = valor_item.getValue().replaceAll("[^\\d]", ""); // Remove caracteres não numéricos

        if (!value.isEmpty()) {
            double valorNumerico = Double.parseDouble(value) / 100;
            valor_item.setValue("R$ " + DECIMAL_FORMAT.format(valorNumerico));
        } else {
            valor_item.setValue("");
        }
        return valor_item;
    }

    public BigDecimal getValorBigDecimal(String valueField) {
        String value = valueField.replaceAll("[^\\d,]", ""); // Remove caracteres exceto dígitos e vírgula

        if (value.isEmpty()) {
            return BigDecimal.ZERO;
        }
        // Substitui vírgula por ponto para criar o BigDecimal
        value = value.replace(",", ".");
        BigDecimal valor = new BigDecimal(value);
        return new BigDecimal(value);
    }

    public void validateSecurityAccessView(SetUsuarios user) {
        if (user.getNome_usuario().startsWith("V")) {
            UI.getCurrent().navigate("access-denied"); // Redireciona para uma página de acesso negado
            return;
        }
    }


    public NumberField formataMoedaBrasileiraNumberField(NumberField valorItem) {
        // Obtém o valor como um número
        Double valorNumerico = valorItem.getValue();

        if (valorNumerico != null) {
            // Formata o valor para o formato monetário brasileiro
            String formattedValue = "R$ " + DECIMAL_FORMAT.format(valorNumerico);
            valorItem.setPlaceholder(formattedValue);

            valorItem.setValue(Double.valueOf(DECIMAL_FORMAT.format(valorNumerico))); // Exibe o valor formatado como label
        } else {
            valorItem.setValue(valorNumerico); // Limpa o label se não houver valor
        }

        return valorItem;
    }

    public void askForConfirmation(Dialog modal) {

        Dialog confirmationDialog = new Dialog();
        confirmationDialog.add("Você realmente deseja sair sem salvar as alterações?");

        Button confirmButton = new Button("Sim", event -> {
            confirmationDialog.close();
            modal.close();
            Notification.show("Você saiu sem salvar.");
        });

        Button cancelButton = new Button("Não", event -> confirmationDialog.close());

        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        confirmationDialog.getFooter().add(confirmButton, cancelButton);
        //confirmationDialog.add(confirmButton, cancelButton);
        confirmationDialog.open();

    }

    public void configureEmailField(EmailField internetEmailField) {
        internetEmailField.getElement().setAttribute("name", "email");
        internetEmailField.setErrorMessage("Insira um endereço de e-mail válido");
        internetEmailField.setClearButtonVisible(true);
    }

    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("#,##0.00'%'");

    public TextField formataPorcentagem(TextField valor_item) {
        // Remove caracteres não numéricos, exceto o ponto decimal
        String value = valor_item.getValue().replaceAll("[^\\d.]", "");

        if (!value.isEmpty()) {
            double valorNumerico = Double.parseDouble(value) / 100; // Divide por 100 para obter porcentagem
            valor_item.setValue(PERCENT_FORMAT.format(valorNumerico));
        } else {
            valor_item.setValue("");
        }
        return valor_item;
    }

    public BigDecimal extrairPorcentagem(BigDecimal valorSomado, BigDecimal porcentagem) {
        // Dividindo a porcentagem por 100 para obter o fator decimal (exemplo: 2% = 0.02)
        BigDecimal fatorPorcentagem = porcentagem.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

        // Multiplicando o valor somado pelo fator porcentagem para extrair o valor da porcentagem
        BigDecimal valorPorcentagem = valorSomado.multiply(fatorPorcentagem).setScale(2, RoundingMode.HALF_UP);

        return valorPorcentagem;
    }
}
