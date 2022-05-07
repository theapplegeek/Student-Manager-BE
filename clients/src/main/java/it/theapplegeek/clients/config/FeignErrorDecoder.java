package it.theapplegeek.clients.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import it.theapplegeek.shared.exception.BadRequestException;
import it.theapplegeek.shared.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() < 500) {
            return switch (response.status()) {
                case 400 -> new BadRequestException(response.body().toString());
                case 404 -> new NotFoundException(response.body().toString());
                default -> new Exception(response.reason());
            };
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
