package br.com.onetec.application.views.layouts.atendimentosHistorico;

import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetContrato;
import br.com.onetec.infra.db.model.SetOrcamento;
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
import com.vaadin.flow.component.notification.Notification;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;

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
                                        String valor) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Itera por cada parágrafo e executa a substituição
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null && text.contains(placeholder)) {
                        text = text.replace(placeholder, replacement);
                        run.setText(text, 0);
                    }
                    if (text != null && text.contains("NOME_CLIENTE")) {
                        text = text.replace("NOME_CLIENTE", cliente.getNome_cliente());
                        run.setText(text, 0);
                    }
                    if (text != null && text.contains("VALOR_CONTRATO")) {
                        text = text.replace("VALOR_CONTRATO", valor);
                        run.setText(text, 0);
                    }if (text != null && text.contains("VALOR_CONTRATO_TEXTO")) {
                        text = text.replace("VALOR_CONTRATO_TEXTO", "Duzentos Reais");
                        run.setText(text, 0);
                    }

                }
            }

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

            // Criar uma nova página PDF e adicionar o conteúdo do Word
            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            // Fluxo de conteúdo para a nova página
            try (PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 700);

                // Itera por cada parágrafo do documento Word e escreve o texto no PDF
                for (XWPFParagraph paragraph : document.getParagraphs()) {
                    //contentStream.showText(paragraph.getText());
                    contentStream.newLine();
                }

                contentStream.endText();
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
