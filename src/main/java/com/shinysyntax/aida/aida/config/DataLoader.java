package com.shinysyntax.aida.aida.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.shinysyntax.aida.aida.entity.Agenda;
import com.shinysyntax.aida.aida.entity.Colaborador;
import com.shinysyntax.aida.aida.entity.RegistroDiario;
import com.shinysyntax.aida.aida.repository.AgendaRepository;
import com.shinysyntax.aida.aida.repository.ColaboradorRepository;
import com.shinysyntax.aida.aida.repository.RegistroDiarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ColaboradorRepository colaboradorRepository;
    private final AgendaRepository agendaRepository;
    private final RegistroDiarioRepository registroRepository;

    public DataLoader(ColaboradorRepository colaboradorRepository, AgendaRepository agendaRepository, RegistroDiarioRepository registroRepository) {
        this.colaboradorRepository = colaboradorRepository;
        this.agendaRepository = agendaRepository;
        this.registroRepository = registroRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // seed colaboradores
        Colaborador c1 = new Colaborador();
        c1.setCpf("11122233344");
        c1.setNome("Maria Silva");
        c1.setEmail("maria@example.com");
        c1.setTelefone("+5511999999999");
        c1.setDataNascimento(LocalDate.of(1990,1,15));
        c1.setCargo("Analista");
        c1.setModalidade(com.shinysyntax.aida.aida.enums.Modalidade.HIBRIDO);
        c1.setDataAdmissao(LocalDate.of(2019, 2, 1));

        Colaborador c2 = new Colaborador();
        c2.setCpf("22233344455");
        c2.setNome("Joao Pereira");
        c2.setEmail("joao@example.com");
        c2.setTelefone("+5511988888888");
        c2.setDataNascimento(LocalDate.of(1985,6,20));
        c2.setCargo("Desenvolvedor");
        c2.setModalidade(com.shinysyntax.aida.aida.enums.Modalidade.REMOTO);
        c2.setDataAdmissao(LocalDate.of(2020, 8, 15));

  
        Colaborador c3 = new Colaborador();
        c3.setCpf("33344455566");
        c3.setNome("Ana Costa");
        c3.setEmail("ana@example.com");
        c3.setTelefone("+5511977777777");
        c3.setDataNascimento(LocalDate.of(1992,3,10));
        c3.setCargo("Designer");
        c3.setProblemaSaude("CID 10 F32: depressão");
        c3.setModalidade(com.shinysyntax.aida.aida.enums.Modalidade.REMOTO);
        c3.setDataAdmissao(LocalDate.of(2022, 7, 1));

        Colaborador c4 = new Colaborador();
        c4.setCpf("44455566677");
        c4.setNome("Carlos Souza");
        c4.setEmail("carlos@example.com");
        c4.setTelefone("+5511966666666");
        c4.setDataNascimento(LocalDate.of(1988,11,5));
        c4.setCargo("Gerente");
        c4.setMedicamentoUsoDiario("102436091: fluexetina");
        c4.setModalidade(com.shinysyntax.aida.aida.enums.Modalidade.PRESENCIAL);
        c4.setDataAdmissao(LocalDate.of(2015, 3, 15));


        colaboradorRepository.save(c1);
        colaboradorRepository.save(c2);
        colaboradorRepository.save(c3);
        colaboradorRepository.save(c4);


        Agenda a1 = new Agenda();
        a1.setTipo("Reunião");
        a1.setDescricao("Reunião de acompanhamento");
        a1.setDataHora(LocalDateTime.now().plusDays(1));
        a1.setPrioridade(com.shinysyntax.aida.aida.enums.Priority.ALTA);
        a1.setPlataforma("Zoom");
        a1.setStatus(com.shinysyntax.aida.aida.enums.AgendaStatus.AGENDADO);
        a1.setColaborador(c1);
        agendaRepository.save(a1);

        Agenda a2 = new Agenda();
        a2.setTipo("Feedback");
        a2.setDescricao("Feedback trimestral com colaborador");
        a2.setDataHora(LocalDateTime.now().plusDays(2));
        a2.setPrioridade(com.shinysyntax.aida.aida.enums.Priority.MEDIA);
        a2.setPlataforma("Teams");
        a2.setStatus(com.shinysyntax.aida.aida.enums.AgendaStatus.AGENDADO);
        a2.setColaborador(c3);
        agendaRepository.save(a2);

        Agenda a3 = new Agenda();
        a3.setTipo("Onboarding");
        a3.setDescricao("Sessão de onboarding");
        a3.setDataHora(LocalDateTime.now().plusDays(3));
        a3.setPrioridade(com.shinysyntax.aida.aida.enums.Priority.BAIXA);
        a3.setPlataforma("Google Meet");
        a3.setStatus(com.shinysyntax.aida.aida.enums.AgendaStatus.AGENDADO);
        a3.setColaborador(c4);
        agendaRepository.save(a3);


        RegistroDiario r1 = new RegistroDiario();
        r1.setDataRegistro(LocalDate.now());
        r1.setEscalaEmocional(5);
        r1.setTempoTela(3);
        r1.setPausasRealizadas(2);
        r1.setObservacoesColaborador("Tudo certo hoje.");
        r1.setColaborador(c1);
        registroRepository.save(r1);

        
        RegistroDiario r2 = new RegistroDiario();
        r2.setDataRegistro(LocalDate.now().minusDays(1));
        r2.setEscalaEmocional(6);
        r2.setTempoTela(4);
        r2.setPausasRealizadas(1);
        r2.setObservacoesColaborador("Dia produtivo.");
        r2.setColaborador(c3);
        registroRepository.save(r2);

        RegistroDiario r3 = new RegistroDiario();
        r3.setDataRegistro(LocalDate.now().minusDays(2));
        r3.setEscalaEmocional(4);
        r3.setTempoTela(2);
        r3.setPausasRealizadas(3);
        r3.setObservacoesColaborador("Algumas pendências a resolver.");
        r3.setColaborador(c4);
        registroRepository.save(r3);
    }
}
