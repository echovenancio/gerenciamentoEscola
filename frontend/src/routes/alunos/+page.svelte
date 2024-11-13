<script lang="ts">
    import { onMount } from "svelte";

    import { Button } from "$lib/components/ui/button";
    import { Checkbox } from "$lib/components/ui/checkbox";
    import { CirclePlus, SquareArrowOutUpRight } from "lucide-svelte";
    import { Separator } from "$lib/components/ui/separator/index.js";
    import * as Table from "$lib/components/ui/table";
    import { readable } from "svelte/store";

    type AlunoDL = {
        id: number,
        nome: string,
        matricula: string,
        media: number
    }

    let data: AlunoDL[] = []
    onMount(async () => {
        try {
            const response = await fetch("http://localhost:8080/alunos");
            if (!response.ok) throw new Error("Errro buscando alunos");
            data = await response.json();
            console.log(data);
        } catch (err) {
            console.log(err);
        }
    })
</script>

<main class="py-4 px-4 flex flex-col gap-4">
    <h1 class="text-2xl">Gerenciamento de Alunos</h1>
    <Button class="max-w-fit">
        <CirclePlus class="mr-2" />
        Adicione um novo aluno
    </Button>
    <Separator class="my-4" />
    <h2>Lista de Alunos</h2>
    <Table.Root>
        <Table.Header>
            <Table.Row>
                <Table.Head></Table.Head>
                <Table.Head class="w-[100px]">Id</Table.Head>
                <Table.Head>Matricula</Table.Head>
                <Table.Head>Nome</Table.Head>
                <Table.Head>Idade</Table.Head>
                <Table.Head>Media</Table.Head>
                <Table.Head class="text-right"></Table.Head>
            </Table.Row>
        </Table.Header>
        <Table.Body>
            {#each data as aluno}
                <Table.Row>
                    <Table.Cell><Checkbox/></Table.Cell>
                    <Table.Cell class="font-medium">{aluno.id}</Table.Cell>
                    <Table.Cell>{aluno.matricula}</Table.Cell>
                    <Table.Cell>{aluno.nome}</Table.Cell>
                    <Table.Cell>{aluno.idade}</Table.Cell>
                    <Table.Cell class="text-right">{aluno.media}</Table.Cell>
                    <Table.Cell class="text-right">
                        <Button href="alunos/{aluno.id}" variant="outline">
                            <SquareArrowOutUpRight/>
                        </Button>
                    </Table.Cell>
                </Table.Row>
            {/each}
        </Table.Body>
    </Table.Root>
</main>
