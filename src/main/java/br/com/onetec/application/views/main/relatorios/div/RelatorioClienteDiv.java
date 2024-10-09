package br.com.onetec.application.views.main.relatorios.div;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.ChartConfig;
import com.byteowls.vaadin.chartjs.config.PieChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.PieDataset;
import com.byteowls.vaadin.chartjs.options.types.BarChartOptions;
import com.byteowls.vaadin.chartjs.options.types.PieChartOptions;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@UIScope
public class RelatorioClienteDiv extends VerticalLayout {


        @Autowired
        public RelatorioClienteDiv() {
            }

}
