package application;

import java.io.IOException;
import java.util.List;

import com.sun.javafx.stage.StageHelper;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AberturaController {
	private boolean MenuAberto = true;
	private boolean MenuSecundarioAluno = false;
	private boolean MenuSecundarioProfessor = false;
	private boolean MenuSecundarioDisciplina = false;
	private boolean MenuSecundarioTurma = false;
	@FXML
	private Button buttonAlunos;
	@FXML
	private Button buttonProfessores;
	@FXML
	private Button buttonDisciplinas;
	@FXML
	private Button buttonTurmas;
	@FXML
	private AnchorPane MenuAnchorPane;
	@FXML
	private Button MenuButton;
	@FXML
	private AnchorPane panelCena;
	@FXML
	private AnchorPane paneAlunos;
	@FXML
	private AnchorPane paneProfessor;
	@FXML
	private AnchorPane paneDisciplina;
	@FXML
	private AnchorPane paneTurma;

	@FXML
	private Button novoAluno;
	@FXML
	private Button editarAluno;
	@FXML
	private Button removerAluno;
	@FXML
	private Button ExibirTodosAlunos;
	@FXML
	private Button exibirAluno;

	@FXML
	private Button novoProfessor;
	@FXML
	private Button editarProfessor;
	@FXML
	private Button removerProfessor;
	@FXML
	private Button ExibirTodosProfessores;
	@FXML
	private Button exibirProfessor;

	@FXML
	private Button novaDisciplina;
	@FXML
	private Button editarDisciplina;
	@FXML
	private Button removerDisciplina;
	@FXML
	private Button ExibirTodasDisciplinas;
	@FXML
	private Button exibirDisciplina;

	@FXML
	private Button novaTurma;
	@FXML
	private Button editarTurma;
	@FXML
	private Button removerTurma;
	@FXML
	private Button ExibirTodasTurmas;
	@FXML
	private Button exibirTurma;
	@FXML
	private Button listaTurmaDeAluno;
	@FXML
	private Button listarTurmaDeProfessor;
	@FXML
	private Button listarTurmaDeDisciplina;

	@FXML
	public void menu1Button(ActionEvent event) {
		if (event.getSource().equals(buttonAlunos)) {
			if (MenuSecundarioProfessor == true) {
				zerarMenuSecundario(paneProfessor);
			}
			if (MenuSecundarioDisciplina == true) {
				zerarMenuSecundario(paneDisciplina);
			}
			if (MenuSecundarioTurma == true) {
				zerarMenuSecundario(paneTurma);
			}
			extenderMenuSecundario(paneAlunos);

		} else if (event.getSource().equals(buttonProfessores)) {
			if (MenuSecundarioAluno == true) {
				zerarMenuSecundario(paneAlunos);
			}
			if (MenuSecundarioDisciplina == true) {
				zerarMenuSecundario(paneDisciplina);
			}
			if (MenuSecundarioTurma == true) {
				zerarMenuSecundario(paneTurma);
			}
			extenderMenuSecundario(paneProfessor);

		} else if (event.getSource().equals(buttonDisciplinas)) {

			if (MenuSecundarioAluno == true) {
				zerarMenuSecundario(paneAlunos);
			}
			if (MenuSecundarioProfessor == true) {
				zerarMenuSecundario(paneProfessor);
			}
			if (MenuSecundarioTurma == true) {
				zerarMenuSecundario(paneTurma);
			}
			extenderMenuSecundario(paneDisciplina);

		} else if (event.getSource().equals(buttonTurmas)) {
			if (MenuSecundarioProfessor == true) {
				zerarMenuSecundario(paneProfessor);
			}
			if (MenuSecundarioDisciplina == true) {
				zerarMenuSecundario(paneDisciplina);
			}
			if (MenuSecundarioAluno == true) {
				zerarMenuSecundario(paneAlunos);
			}
			extenderMenuSecundario(paneTurma);

		}
	}

	@FXML
	public void menu2Button(ActionEvent event) {
		if (event.getSource().equals(novoAluno)) {
			mudarCena("/Aluno/addAluno.fxml");
		} else if (event.getSource().equals(editarAluno)) {
			mudarCena("/Aluno/editarAluno.fxml");
		} else if (event.getSource().equals(exibirAluno)) {
			mudarCena("/Aluno/exibirAluno.fxml");
		} else if (event.getSource().equals(removerAluno)) {
			mudarCena("/Aluno/deletarAluno.fxml");
		} else if (event.getSource().equals(ExibirTodosAlunos)) {
			mudarCena("/Aluno/ListarAluno.fxml");
		} else if (event.getSource().equals(novoProfessor)) {
			mudarCena("/Professor/addProfessor.fxml");
		} else if (event.getSource().equals(editarProfessor)) {
			mudarCena("/Professor/editarProfessor.fxml");
		} else if (event.getSource().equals(removerProfessor)) {
			mudarCena("/Professor/deletarProfessor.fxml");
		} else if (event.getSource().equals(ExibirTodosProfessores)) {
			mudarCena("/Professor/ListarProfessor.fxml");
		} else if (event.getSource().equals(exibirProfessor)) {
			mudarCena("/Professor/exibirProfessor.fxml");
		} else if (event.getSource().equals(novaDisciplina)) {
			mudarCena("/Disciplina/addDisciplina.fxml");
		} else if (event.getSource().equals(editarDisciplina)) {
			mudarCena("/Disciplina/editarDisciplina.fxml");
		} else if (event.getSource().equals(exibirDisciplina)) {
			mudarCena("/Disciplina/exibirDisciplina.fxml");
		} else if (event.getSource().equals(removerDisciplina)) {
			mudarCena("/Disciplina/deletarDisciplina.fxml");
		} else if (event.getSource().equals(ExibirTodasDisciplinas)) {
			mudarCena("/Disciplina/ListarDisciplina.fxml");
		} else if (event.getSource().equals(novaTurma)) {
			mudarCena("/Turma/addTurma.fxml");
		} else if (event.getSource().equals(editarTurma)) {
			mudarCena("/Turma/editarTurma.fxml");
		} else if (event.getSource().equals(removerTurma)) {
			mudarCena("/Turma/deletarTurma.fxml");
		} else if (event.getSource().equals(exibirTurma)) {
			mudarCena("/Turma/exibirTurma.fxml");
		} else if (event.getSource().equals(ExibirTodasTurmas)) {
			mudarCena("/Turma/ListarTurma.fxml");
		} else if (event.getSource().equals(listarTurmaDeProfessor)) {
			mudarCena("/Turma/ListarTurmaDeProfessor.fxml");
		} else if (event.getSource().equals(listaTurmaDeAluno)) {
			mudarCena("/Turma/ListarTurmaDeAluno.fxml");
		}else if (event.getSource().equals(listarTurmaDeDisciplina)) {
			mudarCena("/Turma/ListarTurmaDeDisciplina.fxml");
		}

	}

	public void mudarCena(String nome) {
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getResource(nome));

			List<Node> parentChildren = ((AnchorPane) panelCena.getParent()).getChildren();

			parentChildren.set(parentChildren.indexOf(panelCena), newPane);

			panelCena = newPane;
			extenderMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void extenderMenu() {
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.seconds(0.3));
		slide.setNode(MenuAnchorPane);

		if (MenuAberto == false) {
			slide.setToX(0);

		} else {
			slide.setToX(-230);
			zerarMenuSecundario(paneAlunos);
			zerarMenuSecundario(paneProfessor);
			zerarMenuSecundario(paneDisciplina);
			zerarMenuSecundario(paneTurma);
		}
		slide.play();
		slide.setOnFinished((ActionEvent e) -> {
			if (MenuAberto == false) {
				MenuAberto = true;
			} else {
				MenuAberto = false;
			}
		});

	}

	public AnchorPane checarSecundario() {
		if (MenuSecundarioAluno == true) {
			return paneAlunos;
		} else if (MenuSecundarioProfessor == true) {
			return paneProfessor;
		} else if (MenuSecundarioDisciplina == true) {
			return paneDisciplina;
		} else if (MenuSecundarioTurma == true) {
			return paneTurma;
		}
		System.out.println("retornando nulo de checarSecundario");
		return null;
	}

	private void mudarSecundario(AnchorPane panel) {
		System.out.println("Entrada de Mudar Secundario: " + panel);
		if (panel == paneAlunos) {
			if (MenuSecundarioAluno == false) {
				MenuSecundarioAluno = true;
			} else {
				MenuSecundarioAluno = false;
			}

		} else if (panel == paneProfessor) {
			if (MenuSecundarioProfessor == false) {
				MenuSecundarioProfessor = true;
			} else {
				MenuSecundarioProfessor = false;
			}
		} else if (panel == paneDisciplina) {
			if (MenuSecundarioDisciplina == false) {
				MenuSecundarioDisciplina = true;
			} else {
				MenuSecundarioDisciplina = false;
			}
		} else if (panel == paneTurma) {
			if (MenuSecundarioTurma == false) {
				MenuSecundarioTurma = true;
			} else {
				MenuSecundarioTurma = false;
			}
		}

	}

	public void zerarMenuSecundario(AnchorPane pane) {
		pane.setTranslateX(-460);
		if (pane == paneAlunos) {
			MenuSecundarioAluno = false;
		} else if (pane == paneProfessor) {
			MenuSecundarioProfessor = false;
		} else if (pane == paneDisciplina) {
			MenuSecundarioDisciplina = false;
		} else if (pane == paneTurma) {
			MenuSecundarioTurma = false;
		}
	}

	public void extenderMenuSecundario(AnchorPane pane) {
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.seconds(0.3));
		slide.setNode(pane);
		AnchorPane pn = checarSecundario();
		System.out.println("ExtenderMenuSecundario " + pane);

		if (pn == null) {
			slide.setToX(250);
		} else {
			slide.setToX(-460);
		}
		if (MenuSecundarioAluno == true && pane != paneAlunos) {
			zerarMenuSecundario(paneAlunos);
		}
		if (MenuSecundarioProfessor == true && pane != paneProfessor) {
			zerarMenuSecundario(paneProfessor);
		}
		if (MenuSecundarioDisciplina == true && pane != paneDisciplina) {
			zerarMenuSecundario(paneDisciplina);
		}
		if (MenuSecundarioTurma == true && pane != paneTurma) {
			zerarMenuSecundario(paneTurma);
		}

		slide.play();
		slide.setOnFinished((ActionEvent e) -> {
			System.out.println("ExtenderMenuSecundario parte 2 " + pane);
			mudarSecundario(pane);
		});

	}

}
