<script lang="ts">
    import { onMount } from "svelte";
    import { page } from "$app/stores";
    import { Separator } from "$lib/components/ui/separator";
    import * as Accordion from "$lib/components/ui/accordion";

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
        media: number,
        disciplinas: DisciplinaAluno[]
        turma: Turma,
    }

    let aluno: Partial<Aluno> = {};
    let aprovando: boolean = false;
    onMount(async () => {
        try {
            let response = await fetch("http://localhost:8080/api/alunos/" + id);
            if (!response.ok) throw new Error("error fetching aluno");
            let data = await response.json();
            console.log(data);
            aluno = data;
            if (aluno.media != undefined) {
                aprovando = aluno.media >= 6;
                console.log(aprovando);
            }
        } catch (err) {
            console.log(err);
        }})

</script>

<div class="py-4 flex flex-col gap-4">
    <div class="flex justify-between">
        <div>
            <h1 class="text-5xl uppercase">{aluno.nome}</h1>
            <p class="text-muted-foreground text-sm">Matricula: {aluno.matricula}</p>
        </div>
        <p class="flex flex-col">
            <span 
                class="text-5xl"
                class:text-red-400={!aprovando}
                class:text-green-600={aprovando}
                >
                {aluno.media?.toFixed(1)}
            </span>
            <span class="text-muted-foreground text-sm">Media</span>
        </p>
    </div>
    <Separator/>
    {#if aluno.turma != null}
        <h2 class="text-xl">Turma: <span class="uppercase">{aluno.turma.identificador}</span></h2>
    {/if}
    <Separator/>
    <h2 class="text-xl uppercase underline">Disciplinas</h2>
    <div class="flex gap-4 flex-col">
        {#each aluno.disciplinas ?? [] as disciplina}
            <div class="flex justify-between items-center">
                <span class="text-xl">
                    {disciplina.nome}
                </span>
                <span 
                    class="text-xl"
                    class:text-red-400={disciplina.nota <= 5.0}
                    class:text-green-600={disciplina.nota >= 6.0}
                    >
                    {disciplina.nota.toFixed(1)}
                </span>
            </div>
        {/each}
    </div>
</div>
