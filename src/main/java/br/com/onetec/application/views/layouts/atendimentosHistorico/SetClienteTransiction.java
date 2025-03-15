package br.com.onetec.application.views.layouts.atendimentosHistorico;

import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.cross.utilities.ValorPorExtenso;
import br.com.onetec.infra.db.model.*;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamResource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SetClienteTransiction {

    private static SetCliente cliente;

    public static boolean isRecarregaPagina() {
        return recarregaPagina;
    }

    public static void setRecarregaPagina(boolean recarregaPagina) {
        SetClienteTransiction.recarregaPagina = recarregaPagina;
    }

    private static boolean recarregaPagina;

    public static SetCliente getCliente() {
        return cliente;
    }

    public static void setCliente(SetCliente cliente) {
        SetClienteTransiction.cliente = cliente;
    }



    public static void editWordSentriconDocument(String inputPath, String outputPath, String placeholder,
                                                 String replacement, SetOrcamento orcamento, SetCliente cliente,
                                                 String value, String valor, Integer numeroparcela_pagamentoValue,
                                                 SetCondicaoPagamento id_condicaopagamentoValue, SetEnderecos enderecos,
                                                 SetContrato contrato, SetTipoPagamento tipopag, SetPagamento pagamentoset)
            throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Itera por cada parágrafo e executa a substituição
            // Itera pelos parágrafos do documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        if (text.contains(placeholder)) {
                            text = text.replace(placeholder, replacement);
                        }

                        if (text.contains("Rua Cabedelo, 301 – Butantã – São Paulo - SP")) {
                            text = text.replace("Rua Cabedelo, 301 – Butantã – São Paulo - SP", enderecos.getEnderecoImovel()+ " ," + enderecos.getNumero_imovel());
                        }
                        if (text.contains("VALOR_CONTRATO")) {
                            text = text.replace("VALOR_CONTRATO", valor);
                        }
                        if (text.contains("VALOR1_TEXTO")) {
                            String textovalor = ValorPorExtenso.valorPorExtenso(contrato.getValor_total());
                            text = text.replace("VALOR1_TEXTO", textovalor);
                        }
                        if (text.contains("VALOR_ENTRADA")) {
                            text = text.replace("VALOR_ENTRADA", valor);
                        }
                        if (text.contains("NUM_PARCELA")) {
                            text = text.replace("NUM_PARCELA", ""+numeroparcela_pagamentoValue);
                        }
                        if (text.contains("VALOR_PARCELA")) {
                            text = text.replace("VALOR_PARCELA", ""+pagamentoset.getValor_pagamento());
                        }
                        if (text.contains("TIPO_PAGAMENTO")) {
                            text = text.replace("TIPO_PAGAMENTO", ""+tipopag.getNome_tipopagamento());
                        }
                        if (text.contains("VENCIMENTOS_PAGAMENTO")) {
                            text = text.replace("VENCIMENTOS_PAGAMENTO", "todo dia "+contrato.getDatainicio_vencimento().getDayOfMonth());
                        }
                        if (text.contains("DIA_HOJE")) {
                            text = text.replace("DIA_HOJE", ""+LocalDate.now().getDayOfMonth());
                        }
                        if (text.contains("MES_HOJE")) {
                            String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL,
                                    new Locale("pt", "BR"));
                            text = text.replace("MES_HOJE", ""+mesAtual);
                        }
                        if (text.contains("2018")) {
                            text = text.replace("2018", ""+LocalDate.now().getYear());
                        }
                        if (text.contains("NOME_CLIENTE_ASSINATURA")) {
                            text = text.replace("NOME_CLIENTE_ASSINATURA", cliente.getNome_cliente());
                        }

                        run.setText(text, 0);
                    }
                }
            }

            // Itera pelas tabelas do documento
            document.getTables().forEach(table -> {
                table.getRows().forEach(row -> {
                    row.getTableCells().forEach(cell -> {
                        // Itera pelos parágrafos dentro da célula da tabela
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                String text = run.getText(0);
                                if (text != null) {
                                    if (text.contains(placeholder)) {
                                        text = text.replace(placeholder, replacement);
                                    }
                                    if (text.contains("NOME_CLIENTE")) {
                                        text = text.replace("NOME_CLIENTE", cliente.getNome_cliente());
                                    }
                                    if (text.contains("ENDERECO_CLIENTE")) {
                                        text = text.replace("ENDERECO_CLIENTE", enderecos.getEnderecoImovel()+ " ," + enderecos.getNumero_imovel());
                                    }
                                    if (text.contains("CPF____CNPJ_CLIENTES")) {
                                        text = text.replace("CPF____CNPJ_CLIENTES", cliente.getCpf_cgc_cliente());
                                    }
                                    run.setText(text, 0);
                                }
                            }
                        }
                    });
                });
            });

            // Salva o documento editado
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                document.write(fos);
            }
        }
    }

    // Método para converter o documento editado para PDF
    public static void convertDocxToPdf(String wordFilePath, String pdfFilePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(wordFilePath);
             XWPFDocument document = new XWPFDocument(fis);
             PDDocument pdfDocument = new PDDocument()) {

            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 750);

                for (XWPFParagraph paragraph : document.getParagraphs()) {
                    String text = paragraph.getText();

                    if (text != null && !text.isEmpty()) {
                        // Remova ou substitua caracteres de controle não suportados
                        text = text.replaceAll("[\\t\\n\\r]+", " "); // Substituir TAB e quebras de linha por espaço
                        contentStream.showText(text);
                        contentStream.newLine();
                    }
                }

                contentStream.endText(); // Certifique-se de encerrar o texto corretamente
            }

            pdfDocument.save(pdfFilePath);
        }
    }


    // Método para fazer o download do PDF gerado
    public static void downloadPdf(String pdfPath) throws IOException {
        File pdfFile = new File(pdfPath);
        if (pdfFile.exists()) {
            Notification.show("PDF gerado com sucesso em: " + pdfFile.getAbsolutePath());
        } else {
            Notification.show("Erro: PDF não encontrado.");
        }
    }

    public static void downloadWord(String wordFilePath) throws IOException {
        File wordFile = new File(wordFilePath);

        if (wordFile.exists()) {
            // Cria um recurso de fluxo para o arquivo Word
            StreamResource resource = new StreamResource(wordFile.getName(), () -> {
                try {
                    return new FileInputStream(wordFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            });

            // Adiciona um link para download na interface
            Anchor downloadLink = new Anchor(resource, "Baixar Contrato");
            downloadLink.getElement().setAttribute("download", true);
            downloadLink.getStyle().set("margin-top", "20px");
            downloadLink.getStyle().set("font-size", "18px");

            // Exibe uma notificação de sucesso
            Notification.show("Documento Word disponível para download.");
        } else {
            Notification.show("Erro: Documento Word não encontrado.");
        }
    }

    public static void editWordGeralDocument(String inputPath, String outputPath, String placeholder,
                                             String replacement, SetOrcamento orcamento, SetCliente cliente,
                                             String value, String valor, Integer numeroparcela_pagamentoValue,
                                             SetCondicaoPagamento id_condicaopagamentoValue, SetEnderecos enderecos,
                                             SetContrato contrato, List<SetServico> servicosFilter, Integer meses)
            throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Itera por cada parágrafo e executa a substituição
            // Itera pelos parágrafos do documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        if (text.contains(placeholder)) {
                            text = text.replace(placeholder, replacement);
                        }

                        if (text.contains("PROPOSTA DE ORÇAMENTO Nº _____________")) {
                            text = text.replace("PROPOSTA DE ORÇAMENTO Nº _____________",
                                    "PROPOSTA DE ORÇAMENTO Nº " + orcamento.getId_orcamento());
                        }
                        if (text.contains("NUMECONT")) {
                            text = text.replace("NUMECONT",
                                    ""+ contrato.getId_contrato());
                        }
                        if (text.contains("TEMPOCONT")) {
                            text = text.replace("TEMPOCONT",
                                    meses+ "Meses");
                        }
                        if (text.contains("PRAZOCONT")) {
                            text = text.replace("PRAZOCONT",
                                    meses+ "Meses");
                        }
                        if (text.contains("REF_A")) {
                            text = text.replace("REF_A",""+ cliente.getNome_cliente());
                        }
                        if (text.contains("VALORPAG")) {
                            text = text.replace("VALORPAG","R$ " + valor);
                        }
                        if (text.contains("FORMA_PAG")) {
                            text = text.replace("FORMA_PAG", ""+id_condicaopagamentoValue.getDescricao_condicaopagamento());
                        }
                        if (text.contains("VALOR_PARCELA")) {
                            text = text.replace("VALOR_PARCELA", ""+valor);
                        }
                        if (text.contains("TIPO_PAGAMENTO")) {
                            text = text.replace("TIPO_PAGAMENTO", ""+id_condicaopagamentoValue.getDescricao_condicaopagamento());
                        }
                        if (text.contains("VENCIMENTOS_PAGAMENTO")) {
                            text = text.replace("VENCIMENTOS_PAGAMENTO", ""+LocalDate.now().getDayOfMonth());
                        }
                        if (text.contains("DIA_HOJE")) {
                            text = text.replace("DIA_HOJE", ""+LocalDate.now().getDayOfMonth());
                        }
                        if (text.contains("MES_HOJE")) {
                            text = text.replace("MES_HOJE", ""+LocalDate.now().getMonth());
                        }
                        if (text.contains("2018")) {
                            text = text.replace("2018", ""+LocalDate.now().getYear());
                        }
                        if (text.contains("NOME_CLIENTE_ASSINATURA")) {
                            text = text.replace("NOME_CLIENTE_ASSINATURA", cliente.getNome_cliente());
                        }

                        run.setText(text, 0);
                    }
                }
            }

            // Itera pelas tabelas do documento
            document.getTables().forEach(table -> {
                table.getRows().forEach(row -> {
                    row.getTableCells().forEach(cell -> {
                        // Itera pelos parágrafos dentro da célula da tabela
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                String text = run.getText(0);
                                if (text != null) {
                                    if (text.contains(placeholder)) {
                                        text = text.replace(placeholder, replacement);
                                    }
                                    if (text.contains("Contratante:")) {
                                        text = text.replace("Contratante:","Contratante: "+ cliente.getNome_cliente());
                                    }
                                    if (text.contains("CPF/CNPJ:")) {
                                        text = text.replace("CPF/CNPJ:","CPF/CNPJ: "+ cliente.getCpf_cgc_cliente());
                                    }
                                    if (text.contains("Est.:")) {
                                        text = text.replace("Est.:","Est.: "+ cliente.getIest_cliente());
                                    }
                                    if (text.contains("Legal:")) {
                                        text = text.replace("Legal:","Legal: "+ cliente.getResponsavel_cliente());
                                    }
                                    if (text.contains("Legal:")) {
                                        text = text.replace("Legal:","Legal: "+ cliente.getResponsavel_cliente());
                                    }
                                    if (text.contains("Endereço:")) {
                                        text = text.replace("Endereço:", "Endereço: "+
                                                enderecos.getEnderecoImovel()+ " ," + enderecos.getNumero_imovel());
                                    }
                                    if (text.contains("Bairro:")) {
                                        text = text.replace("Bairro:", "Bairro: "+
                                                enderecos.getBairro_imovel());
                                    }
                                    if (text.contains("CEP:")) {
                                        text = text.replace("CEP:", "CEP: "+
                                                enderecos.getCep_imovel());
                                    }
                                    if (text.contains("Fone:")) {
                                        text = text.replace("Fone:", "Fone: "+
                                                cliente.getTelefone_cliente());
                                    }
                                    if (text.contains("Condições de pagamento:")) {
                                        text = text.replace("Condições de pagamento:", "Condições de pagamento: "+
                                                id_condicaopagamentoValue.getDescricao_condicaopagamento());
                                    }
                                    if (text.contains("Valor total dos serviços acima descritos:")) {
                                        text = text.replace("Valor total dos serviços acima descritos:", "Valor total dos serviços acima descritos:  R$ "+
                                                valor+"                        ");
                                    }
                                    if (text.contains("Serviços a serem realizados:")) {
                                        StringBuilder frase = new StringBuilder();
                                        servicosFilter.forEach(p -> {
                                            frase.append(p.getDescricao_servico()).append(", ");
                                        });
                                        text = text.replace("Serviços a serem realizados:",
                                                "Serviços a serem realizados: "+frase);
                                    }
                                    if (text.contains("CPF____CNPJ_CLIENTES")) {
                                        text = text.replace("CPF____CNPJ_CLIENTES", cliente.getCpf_cgc_cliente());
                                    }
                                    run.setText(text, 0);
                                }
                            }
                        }
                    });
                });
            });

            // Salva o documento editado
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                document.write(fos);
            }
        }
    }

    public static void editWordAnualDocument(String inputPath, String outputPath, String placeholder,
                                             String replacement, SetOrcamento orcamento, SetCliente cliente,
                                             String value, String valor, Integer numeroparcela_pagamentoValue,
                                             SetCondicaoPagamento id_condicaopagamentoValue, SetEnderecos enderecos,
                                             List<SetServico> servicosFilter, SetContrato contrato)
            throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Itera por cada parágrafo e executa a substituição
            // Itera pelos parágrafos do documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
//                        if (text.contains(placeholder)) {
//                            text = text.replace(placeholder, replacement);
//                        }

                        if (text.contains("LOCAIS_TESTE")) {
                            text = text.replace("LOCAIS_TESTE",
                                    enderecos.getEnderecoImovel());
                            //run.setUnderline(UnderlinePatterns.DASH);
                        }
                        if (text.contains("SERVICOS_TESTE")) {
                            StringBuilder frase = new StringBuilder();
                            servicosFilter.forEach(p -> {
                                frase.append(p.getDescricao_servico()).append(", ");
                            });
                            text = text.replace("SERVICOS_TESTE",
                                    frase);
                           // run.setUnderline(UnderlinePatterns.valueOf(text));
                        }
                        if (text.contains("_ETAPAS_TESTE")) {
                                text = text.replace("_ETAPAS_TESTE",
                                    contrato.getQuantidade_aplicacoes().toString());
                           // run.setUnderline(UnderlinePatterns.valueOf(text));
                        }
                        if (text.contains("DATA_INICIO")) {
                            text = text.replace("DATA_INICIO", contrato.getDatainicio_execucao().getDayOfMonth()
                                    +"/"+contrato.getDatainicio_execucao().getMonthValue()+"/"+contrato.getDatainicio_execucao().getYear());
                        }
                        if (text.contains("__ANOTESTE")) {
                            text = text.replace("__ANOTESTE", "1");
                        }
                        if (text.contains("__VALOR_CONTRATO")) {
                            text = text.replace("__VALOR_CONTRATO", "R$ "+valor);
                        }
                        if (text.contains("VALORNAGASAKI")) {
                            text = text.replace("VALORNAGASAKI", "R$ "+value);
                        }
                        if (text.contains("_CONDICAOPAGME")) {
                            text = text.replace("_CONDICAOPAGME", id_condicaopagamentoValue.getDescricao_condicaopagamento());
                        }
                        if (text.contains("DIA_HOJE")) {
                            text = text.replace("DIA_HOJE", ""+LocalDate.now().getDayOfMonth());
                        }
                        if (text.contains("MES_HOJE")) {
                            text = text.replace("MES_HOJE", ""+LocalDate.now().getMonth());
                        }
                        if (text.contains("2018")) {
                            text = text.replace("2018", ""+LocalDate.now().getYear());
                        }
                        if (text.contains("NOME_CLIENTE_ASSINATURA")) {
                            text = text.replace("NOME_CLIENTE_ASSINATURA", cliente.getNome_cliente());
                        }

                        run.setText(text, 0);
                    }
                }
            }

            // Itera pelas tabelas do documento
            document.getTables().forEach(table -> {
                table.getRows().forEach(row -> {
                    row.getTableCells().forEach(cell -> {
                        // Itera pelos parágrafos dentro da célula da tabela
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                String text = run.getText(0);
                                if (text != null) {
                                    if (text.contains(placeholder)) {
                                        text = text.replace(placeholder, replacement);
                                    }
                                    if (text.contains("ontratante")) {
                                        text = text.replace("ontratante","Contratante: "+ cliente.getNome_cliente());
                                    }
                                    if (text.contains("CPF/CNPJ:")) {
                                        text = text.replace("CPF/CNPJ:","CPF/CNPJ: "+ cliente.getCpf_cgc_cliente());
                                    }
                                    if (text.contains("Est.:")) {
                                        text = text.replace("Est.:","Est.: "+ cliente.getIest_cliente());
                                    }
                                    if (text.contains("Cidade:")) {
                                        text = text.replace("Cidade:","Cidade: "+ enderecos.getCidade_imovel());
                                    }
//                                    if (text.contains("UF:")) {
//                                        List<SetEstado> listaEstado = estadoService.listAll();
//                                        SetEstado uf = listaEstado.stream()
//                                                .filter(estado -> estado.getId_estado().equals(enderecos.getId_endereco()))
//                                                .findFirst().orElse(null);
//                                        assert uf != null;
//                                        text = text.replace("UF:","UF: "+ uf.getUf_estado());
//                                    }
                                    if (text.contains("Residencial:")) {
                                        text = text.replace("Residencial:", "Residencial: "+
                                                enderecos.getEnderecoImovel()+ " ," + enderecos.getNumero_imovel());
                                    }
                                    if (text.contains("Bairro:")) {
                                        text = text.replace("Bairro:", "Bairro: "+
                                                enderecos.getBairro_imovel());
                                    }
                                    if (text.contains("CEP:")) {
                                        text = text.replace("CEP:", "CEP: "+
                                                enderecos.getCep_imovel());
                                    }
                                    if (text.contains("Fone:")) {
                                        text = text.replace("Fone:", "Fone: "+
                                                cliente.getTelefone_cliente());
                                    }
                                    if (text.contains("Comercial:")) {
                                        text = text.replace("Comercial:", "Comercial: " +
                                                enderecos.getEnderecoImovel()+ " ," + enderecos.getNumero_imovel());
                                    }
                                    if (text.contains("Contrato:")) {
                                        text = text.replace("Contrato:", "Contrato: " +
                                                contrato.getId_contrato());
                                    }
                                    run.setText(text, 0);
                                }
                            }
                        }
                    });
                });
            });

            // Salva o documento editado
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                document.write(fos);
            }
        }
    }

    public static void editWordFichaInspecaoDocument(String inputPath, String outputPath, String placeholder,
                                                     String replacement, SetOrcamento orcamento, SetCliente cliente,
                                                     String value, String valor, Integer numeroparcela_pagamentoValue,
                                                     SetCondicaoPagamento id_condicaopagamentoValue,
                                                     SetEnderecos enderecos,
                                                     SetContrato contrato,
                                                     List<SetServico> servicosFilter,
                                                     SetEstado uf,
                                                     SetResponsavelCobranca cobranca,
                                                     SetTipoMidia midia,
                                                     SetRegiao regiao, SetTipoImovel tipoImovel,String restecniconome)
            throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Itera por cada parágrafo e executa a substituição
            // Itera pelos parágrafos do documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {

                        if (text.contains("NUM_INDF")) {
                            text = text.replace("NUM_INDF",
                                    orcamento.getId_orcamento().toString());
                        }
                        if (text.contains("RES_TECNICO")) {
                            text = text.replace("RES_TECNICO", restecniconome);
                        }

                        run.setText(text, 0);
                    }
                }
            }

            // Itera pelas tabelas do documento
            document.getTables().forEach(table -> {
                table.getRows().forEach(row -> {
                    row.getTableCells().forEach(cell -> {
                        // Itera pelos parágrafos dentro da célula da tabela
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                String text = run.getText(0);
                                if (text != null) {
                                    if (text.contains("NUM_INDF")) {
                                        text = text.replace("NUM_INDF",
                                                orcamento.getId_orcamento().toString());
                                    }
                                    if (text.contains("SERVC_ID")) {
                                        // Remove o placeholder "SERVC_ID"
                                        text = text.replace("SERVC_ID", "");
                                        run.setText(text, 0); // Atualiza o texto sem "SERVC_ID"

                                        // Adiciona cada descrição de serviço com uma caixa de seleção e quebra de linha
                                        for (SetServico p : servicosFilter) {
                                            run.setText("☒ " + p.getDescricao_servico());
                                            run.addCarriageReturn(); // Adiciona quebra de linha
                                        }
                                    }

                                    if (text.contains("DATA_ORC")) {
                                        // Definir o formato desejado
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        // Formatar a data
                                        String formattedDate = orcamento.getData_inspecao().format(formatter);
                                        text = text.replace("DATA_ORC",
                                                formattedDate);
                                    }
                                    if (text.contains("MIDIA_CLIENTE")) {
                                        text = text.replace("MIDIA_CLIENTE",""+ midia.getDescricao_tipomidia());
                                    }
                                    if (text.contains("NOME_CLIENTE")) {
                                        text = text.replace("NOME_CLIENTE",cliente.getNome_cliente());
                                    }
                                    if (text.contains("PAQ_GUIA")) {
                                        text = text.replace("PAQ_GUIA",
                                                enderecos.getPagina_guia());
                                    }
                                    if (text.contains("ENDERECO_CLIENTE")) {
                                        text = text.replace("ENDERECO_CLIENTE", enderecos.getEnderecoImovel());
                                    }
                                    if (text.contains("NUM_IMOVEL")) {
                                        text = text.replace("NUM_IMOVEL", ""+enderecos.getNumero_imovel());
                                    }
                                    if (text.contains("C_RE")) {
                                        text = text.replace("C_RE", ""+ enderecos.getComplemento_imovel());
                                    }
                                    if (text.contains("BAIRRO_CLIENTE")) {
                                        text = text.replace("BAIRRO_CLIENTE",
                                                enderecos.getBairro_imovel());
                                    }
                                    if (text.contains("CEP_ENDERECO")) {
                                        text = text.replace("CEP_ENDERECO",
                                                enderecos.getCep_imovel());
                                    }
                                    if (text.contains("CIDA_ENDE")) {
                                        text = text.replace("CIDA_ENDE",
                                                enderecos.getCidade_imovel());
                                    }
                                    if (text.contains("UF")) {
                                        text = text.replace("UF",
                                                uf.getUf_estado());
                                    }
                                    if (text.contains("REF_NOME")) {
                                        text = text.replace("REF_NOME",
                                                enderecos.getPonto_referencia());
                                    }
                                    if (text.contains("CONTATO_NOME")) {
                                        text = text.replace("CONTATO_NOME",
                                                cliente.getResponsavel_cliente());
                                    }

                                    if (text.contains("CONTATO_FONE")) {
                                        text = text.replace("CONTATO_FONE",
                                                cliente.getTelefone_cliente());
                                    }
                                    if (text.contains("TIPO_IMOVEL")) {
                                        text = text.replace("TIPO_IMOVEL",
                                                tipoImovel.getDescricao_tipoimovel());
                                    }
                                    if (text.contains("RES_TECNICO")) {
                                        text = text.replace("RES_TECNICO", restecniconome);
                                    }
                                    if (text.contains("SERVICOS_ORCAMENTO")) {
                                        StringBuilder frase = new StringBuilder();
                                        servicosFilter.forEach(p -> {
                                            frase.append(p.getDescricao_servico()).append(", ");
                                        });
                                        text = text.replace("SERVICOS_ORCAMENTO",
                                                frase);
                                    }
                                    if (text.contains("REGIAO_NOME")) {
                                        text = text.replace("REGIAO_NOME", regiao.getDescricao_regiao());
                                    }
                                    if (text.contains("FONE_ENDERECO")) {
                                        text = text.replace("FONE_ENDERECO", enderecos.getTelefone_local());
                                    }
                                    if (text.contains("DATA_INSP")) {
                                        // Definir o formato desejado
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                                        // Formatar a data
                                        String formattedDate = orcamento.getData_inspecao().format(formatter);
                                        text = text.replace("DATA_INSP", formattedDate);
                                    }
                                    if (text.contains("HORARIO_INSP")) {
                                        text = text.replace("HORARIO_INSP", orcamento.getHorario_inspecao().toString());
                                    }
                                    if (text.contains("OBS_CLIENTE")) {
                                        text = text.replace("OBS_CLIENTE", cliente.getObservacoes_cliente());
                                    }
                                    if (text.contains("PROBLEMA_ORCAMENTO")) {
                                        text = text.replace("PROBLEMA_ORCAMENTO", orcamento.getDescricao_problema());
                                    }
                                    if (text.contains("DADOS_ENDERECO")) {
                                        text = text.replace("DADOS_ENDERECO", enderecos.getEnderecoImovel() +","
                                        + enderecos.getNumero_imovel());
                                    }
                                    if (text.contains("EMAIL_CLIENTE")) {
                                        text = text.replace("EMAIL_CLIENTE", cliente.getEmail_cliente());
                                    }
                                    if (Objects.nonNull(cobranca)){
                                        if (text.contains("NOME_COBRANCA")) {
                                            text = text.replace("NOME_COBRANCA", cobranca.getNome_cobranca());
                                        }
                                        if (text.contains("CPFCNPF_COBRANCA")) {
                                            text = text.replace("CPFCNPF_COBRANCA", "");
                                        }
                                        if (text.contains("RGIE_COBRANCA")) {
                                            text = text.replace("RGIE_COBRANCA", cliente.getIest_cliente());
                                        }
                                        if (text.contains("ENDERECO_COBRANCA")) {
                                            text = text.replace("ENDERECO_COBRANCA", cobranca.getObservacao());
                                        }
                                    } else {
                                        if (text.contains("NOME_COBRANCA")) {
                                            text = text.replace("NOME_COBRANCA", "");
                                        }
                                        if (text.contains("CPFCNPF_COBRANCA")) {
                                            text = text.replace("CPFCNPF_COBRANCA", "");
                                        }
                                        if (text.contains("RGIE_COBRANCA")) {
                                            text = text.replace("RGIE_COBRANCA", "");
                                        }
                                        if (text.contains("ENDERECO_COBRANCA")) {
                                            text = text.replace("ENDERECO_COBRANCA", "");
                                        }
                                    }


                                    run.setText(text, 0);
                                }
                            }
                        }
                    });
                });
            });

            // Obtém a política de cabeçalhos e rodapés do documento
            XWPFHeaderFooterPolicy policy = document.getHeaderFooterPolicy();

            // Se o documento tiver rodapé, percorremos seus parágrafos
            if (policy != null) {
                XWPFFooter footer = policy.getDefaultFooter(); // Obtém o rodapé padrão
                if (footer != null) {
                    for (XWPFParagraph paragraph : footer.getParagraphs()) {
                        for (XWPFRun run : paragraph.getRuns()) {
                            String text = run.getText(0);
                            if (text != null) {
                                if (text.contains("RES_TECNICO")) {
                                    text = text.replace("RES_TECNICO", restecniconome);
                                }
                                run.setText(text, 0);
                            }
                        }
                    }
                }
            }

            // Salva o documento editado
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                document.write(fos);
            }
        }
    }

    public static void editWordFichaOrdemDocument(String inputPath, String outputPath, String placeholder,
                                                  SetOrdemServico ordemServico, SetCliente cliente,
                                                  SetEnderecos enderecos,
                                                  SetContrato contrato,
                                                  SetEstado uf,
                                                  SetResponsavelCobranca cobranca,
                                                  SetTipoMidia midia, SetFuncionario funcionario,
                                                  SetRegiao regiao, SetTipoImovel tipoImovel, String diasemana,
                                                  List<SetOrdemServicoExecucaoServico> listaservicosexecucaodetalhadas,
                                                  SetTipoAtendimento tipoordem, List<SetExecucaoServico> servicoList,
                                                  String restecniconome)
            throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Itera por cada parágrafo e executa a substituição
            // Itera pelos parágrafos do documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {

                        if (text.contains("ENDERECO_CLIENTE")) {
                            text = text.replace("ENDERECO_CLIENTE", enderecos.getEnderecoImovel());
                        }
                        if (text.contains("PAG_GUI")) {
                            text = text.replace("PAG_GUI",
                                    enderecos.getPagina_guia());
                        }
                        if (text.contains("CPF_CNPJ")) {
                            text = text.replace("CPF_CNPJ",
                                    cliente.getCpf_cgc_cliente());
                        }
                        if (text.contains("BAIRRO_C")) {
                            text = text.replace("BAIRRO_C",
                                    enderecos.getBairro_imovel());
                        }
                        if (text.contains("CEP_C")) {
                            text = text.replace("CEP_C",
                                    enderecos.getCep_imovel());
                        }
                        if (text.contains("CIDADE_C")) {
                            text = text.replace("CIDADE_C",
                                    enderecos.getCidade_imovel());
                        }
                        if (text.contains("UF")) {
                            text = text.replace("UF",
                                    uf.getUf_estado());
                        }
                        if (text.contains("FONE_C")) {
                            text = text.replace("FONE_C",
                                    cliente.getTelefone_cliente());
                        }
                        if (text.contains("NOME_PONT")) {
                            text = text.replace("NOME_PONT",
                                    ordemServico.getNome_pontofocal());
                        }
                        if (text.contains("REP_APROVA")) {
                            text = text.replace("REP_APROVA",
                                    cliente.getNome_contato_cliente());
                        }
                        if (text.contains("TP_MIDIA")) {
                            text = text.replace("TP_MIDIA",""+ midia.getDescricao_tipomidia());
                        }
                        if (text.contains("TEC_OS")) {
                            text = text.replace("TEC_OS",""+ funcionario.getNome_funcionario());
                        }
                        if (text.contains("PONTO_REFFF")) {
                            text = text.replace("PONTO_REFFF",""+ enderecos.getPonto_referencia());
                        }
                        if (text.contains("RESP_TECNICO")) {
                            text = text.replace("RESP_TECNICO", restecniconome);
                        }
                        SetExecucaoServico servicoseleci = null;
                        if (listaservicosexecucaodetalhadas.size() > 0){

                            servicoseleci = servicoList.stream()
                                    .filter(objeto -> objeto.getId_execucaoservico().equals(listaservicosexecucaodetalhadas.get(0).getId_execucaoservico()))
                                    .findFirst().orElse(new SetExecucaoServico());
                            if (text.contains("SERVICO_TIPO1")) {
                                text = text.replace("SERVICO_TIPO1", "" + servicoseleci.getNome_execucaoservico());
                            }
                            if (text.contains("DESCRICAO_SERVICO1")) {
                                text = text.replace("DESCRICAO_SERVICO1", "" + listaservicosexecucaodetalhadas.get(0).getDescricao_ordemservicoexecucaoservico());
                            }
                            if (listaservicosexecucaodetalhadas.size() > 1){
                                servicoseleci = servicoList.stream()
                                        .filter(objeto -> objeto.getId_execucaoservico().equals(listaservicosexecucaodetalhadas.get(1).getId_execucaoservico()))
                                        .findFirst().orElse(new SetExecucaoServico());
                                if (text.contains("SERVICO_TIPO2")) {
                                    text = text.replace("SERVICO_TIPO2", "" + servicoseleci.getNome_execucaoservico());
                                }
                                if (text.contains("DESCRICAO_SERVICO2")) {
                                    text = text.replace("DESCRICAO_SERVICO2", "" + listaservicosexecucaodetalhadas.get(1).getDescricao_ordemservicoexecucaoservico());
                                }
                            } else {
                                if (text.contains("SERVICO_TIPO2")) {
                                    text = text.replace("SERVICO_TIPO2", "");
                                }
                                if (text.contains("DESCRICAO_SERVICO2")) {
                                    text = text.replace("DESCRICAO_SERVICO2", "");
                                }
                                if (text.contains("SERVICO_TIPO3")) {
                                    text = text.replace("SERVICO_TIPO3", "");
                                }
                                if (text.contains("DESCRICAO_SERVICO3")) {
                                    text = text.replace("DESCRICAO_SERVICO3", "");
                                }
                            }
                            if (listaservicosexecucaodetalhadas.size() > 2) {
                                servicoseleci = servicoList.stream()
                                        .filter(objeto -> objeto.getId_execucaoservico().equals(listaservicosexecucaodetalhadas.get(2).getId_execucaoservico()))
                                        .findFirst().orElse(new SetExecucaoServico());
                                if (text.contains("SERVICO_TIPO3")) {
                                    text = text.replace("SERVICO_TIPO3", "" + servicoseleci.getNome_execucaoservico());
                                }
                                if (text.contains("DESCRICAO_SERVICO3")) {
                                    text = text.replace("DESCRICAO_SERVICO3", "" + listaservicosexecucaodetalhadas.get(2).getDescricao_ordemservicoexecucaoservico());
                                }
                            } else {
                                if (text.contains("SERVICO_TIPO3")) {
                                    text = text.replace("SERVICO_TIPO3", "");
                                }
                                if (text.contains("DESCRICAO_SERVICO3")) {
                                    text = text.replace("DESCRICAO_SERVICO3", "");
                                }
                            }
                        } else {
                            if (text.contains("SERVICO_TIPO1")) {
                                text = text.replace("SERVICO_TIPO1", "N/D" );
                            }
                            if (text.contains("DESCRICAO_SERVICO1")) {
                                text = text.replace("DESCRICAO_SERVICO1", "" );
                            }
                            if (text.contains("SERVICO_TIPO2")) {
                                text = text.replace("SERVICO_TIPO2", "");
                            }
                            if (text.contains("DESCRICAO_SERVICO2")) {
                                text = text.replace("DESCRICAO_SERVICO2", "");
                            }
                            if (text.contains("SERVICO_TIPO3")) {
                                text = text.replace("SERVICO_TIPO3", "");
                            }
                            if (text.contains("DESCRICAO_SERVICO3")) {
                                text = text.replace("DESCRICAO_SERVICO3", "");
                            }
                        }



                        run.setText(text, 0);

                    }
                }
            }

            // Itera pelas tabelas do documento
            document.getTables().forEach(table -> {
                        table.getRows().forEach(row -> {
                            row.getTableCells().forEach(cell -> {
                                // Itera pelos parágrafos dentro da célula da tabela
                                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                                    for (XWPFRun run : paragraph.getRuns()) {
                                        String text = run.getText(0);
                                        if (text != null) {
                                            if (text.contains("NOME_CLIENTE")) {
                                                text = text.replace("NOME_CLIENTE",
                                                        cliente.getNome_cliente());
                                            }
                                            if (text.contains("ENDERECO_CLIENTE")) {
                                                text = text.replace("ENDERECO_CLIENTE", enderecos.getEnderecoImovel());
                                            }
                                            if (text.contains("PAG_GUI")) {
                                                text = text.replace("PAG_GUI",
                                                        enderecos.getPagina_guia());
                                            }
                                            if (text.contains("CPF_CNPJ")) {
                                                text = text.replace("CPF_CNPJ",
                                                        cliente.getCpf_cgc_cliente());
                                            }
                                            if (text.contains("BAIRRO_C")) {
                                                text = text.replace("BAIRRO_C",
                                                        enderecos.getBairro_imovel());
                                            }
                                            if (text.contains("CEP_C")) {
                                                text = text.replace("CEP_C",
                                                        enderecos.getCep_imovel());
                                            }
                                            if (text.contains("CIDADE_C")) {
                                                text = text.replace("CIDADE_C",
                                                        enderecos.getCidade_imovel());
                                            }
                                            if (text.contains("UF")) {
                                                text = text.replace("UF",
                                                        uf.getUf_estado());
                                            }
                                            if (text.contains("FONE_C")) {
                                                text = text.replace("FONE_C",
                                                        cliente.getTelefone_cliente());
                                            }
                                            if (text.contains("NOME_PONT")) {
                                                text = text.replace("NOME_PONT",
                                                        ordemServico.getNome_pontofocal());
                                            }
                                            if (text.contains("REP_APROVA")) {
                                                text = text.replace("REP_APROVA",
                                                        cliente.getNome_contato_cliente());
                                            }
                                            if (text.contains("TP_MIDIA")) {
                                                text = text.replace("TP_MIDIA",""+ midia.getDescricao_tipomidia());
                                            }
                                            if (text.contains("TEC_OS")) {
                                                text = text.replace("TEC_OS",""+ funcionario.getNome_funcionario());
                                            }
                                            if (text.contains("PONTO_REFFF")) {
                                                text = text.replace("PONTO_REFFF",""+ enderecos.getPonto_referencia());
                                            }
                                            if (text.contains("RES_TECNICO")) {
                                                text = text.replace("RES_TECNICO", restecniconome);
                                            }
                                            if (text.contains("data_ate")) {
                                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                                String formattedDate = ordemServico.getDatainicio_ordemservico().format(formatter);
                                                text = text.replace("data_ate", formattedDate);
                                            }
                                            if (text.contains("dia_sem")) {
                                                text = text.replace("dia_sem", diasemana);
                                            }
                                            if (text.contains("hora_at")) {
                                                text = text.replace("hora_at", ordemServico.getHorarioinicio_ordemservico().toString());
                                            }
                                            if (text.contains("num_orca")) {
                                                text = text.replace("num_orca", ordemServico.getId_orcamento().toString());
                                            }
                                            if (text.contains("numcont")) {
                                                text = text.replace("numcont", contrato.getId_contrato().toString());
                                            }
                                            if (text.contains("NUMEROOS")) {
                                                text = text.replace("NUMEROOS", ordemServico.getId_ordemservico().toString());
                                            }
                                            if (text.contains("TIPO_ORDEMSEC")) {
                                                text = text.replace("TIPO_ORDEMSEC", tipoordem.getDescricao_tipoatendimento());
                                            }
                                            if (text.contains("RESP_TECNICO")) {
                                                text = text.replace("RESP_TECNICO", restecniconome);
                                            }
                                            run.setText(text, 0);
                                        }
                                    }
                                }
                            });
                        });
                    });

                    // Obtém a política de cabeçalhos e rodapés do documento
                    XWPFHeaderFooterPolicy policy = document.getHeaderFooterPolicy();

                    // Se o documento tiver rodapé, percorremos seus parágrafos
                    if (policy != null) {
                        XWPFFooter footer = policy.getDefaultFooter(); // Obtém o rodapé padrão
                        if (footer != null) {
                            for (XWPFParagraph paragraph : footer.getParagraphs()) {
                                for (XWPFRun run : paragraph.getRuns()) {
                                    String text = run.getText(0);
                                    if (text != null) {
                                        if (text.contains("RESP_TECNICO")) {
                                            text = text.replace("RESP_TECNICO", restecniconome);
                                        }
                                        run.setText(text, 0);
                                    }
                                }
                            }
                        }
                    }

            // Salva o documento editado
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                document.write(fos);
            }
        }
    }


    public  ByteArrayInputStream EditDocAndGeneratePdf (){

            String inputPdfPath =
                    "C:\\Users\\Danilo Luiz\\Downloads\\ACFrOgBaV9-DtCdVufr97aNzBj-vf31RLYOqyF9aDmtuHRwiavohJ84Ljm2IeynaqsIyZyd9W5Jv4OfXQsU74Sa0d_gI52Uv_26KZLD7CfvEmMdsqEmStku2gt1zRsogUex5UYHJan-CrkNo80sy.pdf";
            String outputDocPath = "C:\\Users\\Danilo Luiz\\Downloads\\Matriz Contrato Sentricon (instalação)_editado.doc";
            String outputPdfPath = "C:\\Users\\Danilo Luiz\\Downloads\\Matriz Contrato Sentricon (instalação)_editado.pdf";
            String newValue = "12345"; // Novo valor para substituir "81038"
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // Leitura do PDF original
            PdfReader reader = new PdfReader(new FileInputStream(inputPdfPath));
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);

            // Modificar o texto no PDF
            for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
                // Extração do conteúdo da página
                String pageContent = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i), new LocationTextExtractionStrategy());

                if (pageContent.contains("81038")) {
                    // Use PdfCanvas para apagar o texto antigo e redesenhar o novo texto
                    PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(i));
                    // Obter a posição do texto original (você pode precisar ajustar a lógica aqui)
                    float x = 10;  // Ajuste a coordenada X conforme necessário
                    float y = 500; // Ajuste a coordenada Y conforme necessário


                    // Obter uma fonte padrão
                    PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

                    // Altura do texto pode ser aproximadamente o tamanho da fonte
                    float fontSize = 12;
                    float textHeight = fontSize;

                    // Desenhar um retângulo em branco sobre o texto antigo
                    float oldTextWidth = font.getWidth("81038", fontSize);
                    Rectangle rect = new Rectangle(x, y - textHeight, oldTextWidth, textHeight);
                    canvas.saveState();
                    canvas.setFillColor(new DeviceGray(1.0f)); // Cor branca para "apagar" o texto original
                    canvas.rectangle(rect);
                    canvas.fill();
                    canvas.restoreState();

                    // Adicione o novo texto
                    canvas.beginText()
                            .setFontAndSize(font, fontSize)
                            .moveText(x, y)
                            .showText(newValue)
                            .endText();
                }
            }

            // Fechar o documento
            pdfDoc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(baos.toByteArray());
    }

}
