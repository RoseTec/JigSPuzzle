package jigspuzzle.view.desktop.swing;

import java.awt.Component;
import javax.swing.JOptionPane;
import jigspuzzle.controller.SettingsController;

/**
 * Presents an error message to the user.
 *
 * @author RoseTec
 */
public class ErrorMessageDialog {

    /**
     * The title of the dialog
     */
    private String title;

    /**
     * The message to be displayed
     */
    private String message;

    /**
     * A detailed error log that can be added to be displayed.
     */
    private String errorMessage;

    /**
     * Creates a new error message.
     *
     * @param title The title for the dialog
     * @param message The message to be displayed
     */
    public ErrorMessageDialog(String title, String message) {
        this(title, message, null);
    }

    /**
     * Creates a new error message.
     *
     * @param title The title for the dialog
     * @param message The message to be displayed
     * @param errorMessage Sometimes, an error log is given when an error
     * occures. This error log is displayed here nicely.
     */
    public ErrorMessageDialog(String title, String message, String errorMessage) {
        this.title = title;
        this.message = message;
        this.errorMessage = errorMessage;
    }

    /**
     * Sets the button text, where the user has to click on to close the dialog.
     *
     * @param newText
     */
    public void setButtonText(String newText) {
    }

    /**
     * Shows the dialog to be user
     *
     * @param parent The parent of the Dialog.
     */
    public void showDialog(Component parent) {
        String[] options = {this.getButtonText()};
        String messageToShow = message;

        if (errorMessage != null) {
            messageToShow += "\n\n" + SettingsController.getInstance().getLanguageText(5, 10) + "\n" + errorMessage;
        }
        JOptionPane.showOptionDialog(parent,
                messageToShow, // todo: make it nicer..
                title,
                JOptionPane.OK_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                options,
                options[0]);
    }

    /**
     * @return The button text, where the user has to click on to close the
     * dialog.
     */
    private String getButtonText() {
        return SettingsController.getInstance().getLanguageText(5, 20);
    }

}
