package com.simao.tarea3AD2024base.config;

import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpManager {

    public static void mostrarAyuda() {

        WebView webView = new WebView();

        String url = HelpManager.class
                .getResource("/help/ayuda.html")
                .toExternalForm();

        webView.getEngine().load(url);

        Stage stage = new Stage();
        stage.setTitle("Ayuda");
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(new Scene(webView, 900, 600));
        stage.show();
    }
}
