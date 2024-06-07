package org.lisandroJimenez.report;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.lisandroJimenez.dao.Conexion;
import win.zqxu.jrviewer.JRViewerFX;


/**
 *
 *
 *
 * @author informatica
 *
 */
public class GenerarReporte {
    private static GenerarReporte instance;
    private static Connection conexion = null;
    private GenerarReporte() {
    }
    public static GenerarReporte getInstance() {
        if (instance == null) {
            instance = new GenerarReporte();
        }
        return instance;
    }
    public void generarFactura(int factId) {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("facId", factId);
            InputStream jasperPath = GenerarReporte.class.getResourceAsStream("/org/lisandroJimenez/report/Factura.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath, parametros, conexion);
            Stage reportStage = new Stage();
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            Pane root = new Pane();
            root.getChildren().add(reportViewer);
            reportViewer.setPrefSize(1000, 800);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Factura");
            reportStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void generarCliente() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            InputStream jasperPath = GenerarReporte.class.getResourceAsStream("/org/lisandroJimenez/report/Cliente.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath, null, conexion);
            Stage reportStage = new Stage();
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            Pane root = new Pane();
            root.getChildren().add(reportViewer);
            reportViewer.setPrefSize(1000, 800);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Clientes");
            reportStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void generarProducto() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            InputStream jasperPath = GenerarReporte.class.getResourceAsStream("/org/lisandroJimenez/report/Producto.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath, null, conexion);
            Stage reportStage = new Stage();
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            Pane root = new Pane();
            root.getChildren().add(reportViewer);
            reportViewer.setPrefSize(1000, 800);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Productos");
            reportStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
