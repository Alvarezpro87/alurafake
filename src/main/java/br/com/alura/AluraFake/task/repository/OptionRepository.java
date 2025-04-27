package br.com.alura.AluraFake.task.repository;

import br.com.alura.AluraFake.task.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {}