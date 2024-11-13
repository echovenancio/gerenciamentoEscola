<script lang="ts">
    import { onMount } from "svelte";
    import { page } from "$app/stores";

    $: id = $page.params.id;

    interface DisciplinaAluno {
        id: number,
        nome: string,
        nota: number,
    }

    interface Turma {
        id: number,
        identificador: string,
    }

    interface Aluno {
        id: number,
        matricula: string,
        nome: string,
        disciplinas: DisciplinaAluno[]
        turma: Turma,
    }

    let aluno: Partial<Aluno> = {};
    onMount(async () => {
        try {
            let response = await fetch("http://localhost:8080/alunos/" + id);
            if (!response.ok) throw new Error("error fetching aluno");
            let data = await response.json();
            console.log(data);
            aluno = data;
        } catch (err) {
            console.log(err);
        }})
</script>

<main class="py-4 px-4 flex flex-col gap-4">
    Id: {aluno.id} <br>
    Nome: {aluno.nome} <br>
    Matricula: {aluno.matricula} <br>
    disciplinas: <br>
    {#each aluno.disciplinas ?? [] as disciplina}
        Id: {disciplina.id} <br>
        nome: {disciplina.nome} <br>
        nota: {disciplina.nota} <br>
    {/each}
</main>
