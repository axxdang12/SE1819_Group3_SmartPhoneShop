package swp391.SPS.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(NotFoundUserOrWrongException.class)
    public String handlerNotFoundUserOrWrongException(
            NotFoundUserOrWrongException exception, Model model) {
        model.addAttribute("message",
                "User not found or wrong password and user name!");
        return "login";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handlerUserNotFoundException(
            UserNotFoundException exception, Model model) {
        model.addAttribute("message",
                exception.getMessage());
        return "forgot-password-form";
    }

    @ExceptionHandler(NoDataInListException.class)
    public String handlerNoDataInListException(
            NoDataInListException exception, Model model) {
        model.addAttribute("message",
                exception.getMessage());
        return "admin-dashboard";
    }

    @ExceptionHandler(OutOfPageException.class)
    public String handlerOutOfPageException(
            OutOfPageException exception, Model model) {
        model.addAttribute("message",
                exception.getMessage());
        return "admin-dashboard";
    }

    @ExceptionHandler(FileNotFoundException.class)
    public String handlerFileNotFoundException(FileNotFoundException fileNotFoundException, Model model) {
        model.addAttribute("message",
                fileNotFoundException.getMessage());
        return "404";
    }
//    @ExceptionHandler(FileNotFoundException.class)
//    public String handlerProductNotFoundException(FileNotFoundException fileNotFoundException, Model model) {
//        model.addAttribute("message",
//                fileNotFoundException.getMessage());
//        return "NotFound";
//    }
}
