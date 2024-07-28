package swp391.SPS.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
                "Could not find any customer with the email");
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

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException ex, Model model) {
        model.addAttribute("message", "You do not have permission to access this page");
        return "403";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handlerNoResourceFoundException(NoResourceFoundException noResourceFoundException, Model model) {
        model.addAttribute("message",
                noResourceFoundException.getMessage());
        return "404";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, Model model) {
        model.addAttribute("message",
                methodArgumentTypeMismatchException.getMessage());
        return "404";
    }


//    @ExceptionHandler(FileNotFoundException.class)
//    public String handlerProductNotFoundException(FileNotFoundException fileNotFoundException, Model model) {
//        model.addAttribute("message",
//                fileNotFoundException.getMessage());
//        return "NotFound";
//    }
}
