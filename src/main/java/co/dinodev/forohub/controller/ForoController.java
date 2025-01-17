package co.dinodev.forohub.controller;

import co.dinodev.forohub.domain.topico.Topico;
import co.dinodev.forohub.domain.topico.TopicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topico")
public class ForoController {

    @Autowired
    TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity crearNuevoTopico(@RequestBody Topico topico, UriComponentsBuilder uriComponentsBuilder){
        Topico topicoNuevo = topicoRepository.save(topico);
        URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topicoNuevo.getId()).toUri();
        return ResponseEntity.created(url).body(topicoNuevo);
    }

    @GetMapping
    public ResponseEntity todosLosTopicos(){
        return ResponseEntity.ok(topicoRepository.findAll());
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity mostrarTopico(@PathVariable Long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()){
            return ResponseEntity.ok(topico);
        }else {
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody Topico topico, @PathVariable Long id){
            Topico topicoValidado = topicoRepository.findById(id).get();
            topicoValidado.setTitulo(topico.getTitulo());
            topicoValidado.setMensaje(topico.getMensaje());
            return ResponseEntity.ok(topicoValidado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return (ResponseEntity) ResponseEntity.noContent();
    }
}
