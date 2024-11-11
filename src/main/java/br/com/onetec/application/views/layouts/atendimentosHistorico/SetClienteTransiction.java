package br.com.onetec.application.views.layouts.atendimentosHistorico;

import br.com.onetec.infra.db.model.SetCliente;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

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
