<script lang="ts">
    import { onMount } from "svelte";
    import { page } from "$app/stores";
    import { Separator } from "$lib/components/ui/separator";

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
        media?: number,
        disciplinas: DisciplinaAluno[]
        turma: Turma,
    }

    let aluno: Partial<Aluno> = {};
    let aprovando = false;
    onMount(async () => {
        try {
            let response = await fetch("http://localhost:8080/api/alunos/" + id);
            if (!response.ok) throw new Error("error fetching aluno");
            let data = await response.json();
            console.log(data);
            aluno = data;
            aprovando = aluno.media ?? 0 >= 6 ? true : false
        } catch (err) {
            console.log(err);
        }})

</script>

<div class="py-4 flex flex-col gap-4">
    <div class="flex justify-between">
        <div>
            <h1 class="text-5xl">{aluno.nome}</h1>
            <p class="text-muted-foreground text-sm">Matricula: {aluno.matricula}</p>
        </div>
        <p class="flex flex-col">
            <span>Media:</span>
            <span 
                class="text-5xl text-red-600"
                class:text-green-600={aprovando}
                >
                {aluno.media?.toFixed(1)}
            </span>
        </p>
    </div>
    <Separator/>
    {#if aluno.turma != null}
        Turma: {aluno.turma.identificador}
    {/if}
    disciplinas: <br>
    {#each aluno.disciplinas ?? [] as disciplina}
        Id: {disciplina.id} <br>
        nome: {disciplina.nome} <br>
        nota: {disciplina.nota} <br>
    {/each}
</div>
