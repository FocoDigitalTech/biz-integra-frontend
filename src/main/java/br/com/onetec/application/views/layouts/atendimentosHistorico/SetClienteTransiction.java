package br.com.onetec.application.views.layouts.atendimentosHistorico;

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
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.time.LocalDate;

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

    public static void editWordDocument(String inputPath, String outputPath, String placeholder,
                                        String replacement, SetOrcamento orcamento, SetCliente cliente,
                                        String value, String valor, Integer numeroparcela_pagamentoValue, SetCondicaoPagamento id_condicaopagamentoValue, SetEnderecos enderecos) throws IOException {
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
                        if (text.contains("VALOR_CONTRATO_TEXTO")) {
                            text = text.replace("VALOR_CONTRATO_TEXTO", valor);
                        }
                        if (text.contains("VALOR_ENTRADA")) {
                            text = text.replace("VALOR_ENTRADA", valor);
                        }
                        if (text.contains("NUM_PARCELA")) {
                            text = text.replace("NUM_PARCELA", ""+numeroparcela_pagamentoValue);
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
