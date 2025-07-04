package rosario.chumpitaz.reniec.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rosario.chumpitaz.reniec.service.ReniecService;
import rosario.chumpitaz.reniec.model.DataReniec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/reniec")
@AllArgsConstructor
public class ReniecRest {

    private final ReniecService reniecService;

    @GetMapping
    public Flux<DataReniec> getAll() {
        return reniecService.getAll();
    }

    @GetMapping("/ruc/{ruc}")
    public Mono<DataReniec> getByRuc(@PathVariable String ruc) {
        return reniecService.fetchAndSaveRucData(ruc);
    }

    @PutMapping("/desactivar/{id}")
    public Mono<DataReniec> desactivar(@PathVariable Long id) {
        return reniecService.desactivar(id);
    }

    @PutMapping("/restaurar/{id}")
    public Mono<DataReniec> restaurar(@PathVariable Long id) {
        return reniecService.restaurar(id);
    }
}