package bugglequest.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import bugglequest.core.Game;

public class Reset extends AbstractGameAction {

	private static final long serialVersionUID = 5113775865916404566L;

	public Reset(Game game, String text, ImageIcon icon) {
		super(game, text, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.game.reset();
	}
}
