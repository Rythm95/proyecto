package com.simao.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	ADMINISTRADOR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("admin.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Admin.fxml";
		}
	},
	ESTUDIANTE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("estudiante.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Estudiante.fxml";
		}
	},
	PROFESORADO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("profesorado.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Profesorado.fxml";
		}
	},
	TUTOR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tutor.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Tutor.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
