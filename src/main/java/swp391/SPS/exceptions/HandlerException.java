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

}
