<script lang="ts">
    import { onMount } from "svelte";

    import { Button } from "$lib/components/ui/button";
    import { CirclePlus } from "lucide-svelte";
    import { Separator } from "$lib/components/ui/separator/index.js";
    import * as Table from "$lib/components/ui/table";

    let data: any = []
    onMount(async () => {
        try {
            const response = await fetch("http://localhost:8080/turmas");
            if (!response.ok) throw new Error("Errro buscando turmas");
            data = await response.json();
            console.log(data);
        } catch (err) {
            console.log(err);
        }
    })
</script>

<main class="py-4 px-4 flex flex-col gap-4">
    <h1 class="text-2xl">Gerenciamento de Turmas</h1>
    <Button class="max-w-fit">
        <CirclePlus class="mr-2" />
        Adicione uma nova torma
    </Button>
    <Separator class="my-4" />
    <h2>Lista de Alunos</h2>
    <Table.Root>
        <Table.Header>
            <Table.Row>
                <Table.Head class="w-[100px]">Id</Table.Head>
                <Table.Head class="text-right">Identificador</Table.Head>
            </Table.Row>
        </Table.Header>
        <Table.Body>
            {#each data as turma}
                <Table.Row>
                    <Table.Cell class="font-medium">{turma.id}</Table.Cell>
                    <Table.Cell class="text-right">{turma.identificador}</Table.Cell>
                </Table.Row>
            {/each}
        </Table.Body>
    </Table.Root>
</main>
