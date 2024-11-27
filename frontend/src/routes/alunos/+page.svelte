<script lang="ts">
    import { onMount } from "svelte";

    import { Button } from "$lib/components/ui/button";
    import { Checkbox } from "$lib/components/ui/checkbox";
    import { CirclePlus, SquareArrowOutUpRight, ChevronDown } from "lucide-svelte";
    import { Separator } from "$lib/components/ui/separator/index.js";
    import * as Table from "$lib/components/ui/table";
    import * as DropdownMenu from "$lib/components/ui/dropdown-menu/index.js";
    import { page } from "$app/stores"
    import * as Dialog from "$lib/components/ui/dialog/index.js";
    import { toast } from "svelte-sonner";
    import { Toaster } from "$lib/components/ui/sonner";

    type AlunoDL = {
        id: number,
        nome: string,
        matricula: string,
        media: number,
        idade: number
    }


    async function fetchAlunos()  {
        try {
            const response = await fetch("http://localhost:8080/api/alunos");
            if (!response.ok) throw new Error("Errro buscando alunos");
            return response.json();
        } catch (err) {
            console.log(err);
        }
    }

    let data: AlunoDL[] = $state([])

    onMount(async () => {
        data = await fetchAlunos();
    })

    async function deleteAluno(id: number) {
        try {
            console.log(id);
            const url = "http://localhost:8080/api/alunos/" + id;
            const response = await fetch(
               url, { method: "delete" } 
            );
            const code = response.status;
            if (code == 200) {
                console.log("aluno excluido");
            } else {
                console.log("deu pau");
            }
        } catch (err) {
            console.log(err);
        }
        data = await fetchAlunos();
    }

    </script>

<div class="py-4 flex flex-col gap-4">
    <h1 class="text-2xl">Gerenciamento de Alunos</h1>
    <Button class="max-w-fit" href={$page.url + "/new"}>
        <CirclePlus class="mr-2" />
        Adicione um novo aluno
    </Button>
    <Separator class="my-4" />
    <h2>Lista de Alunos</h2>
    <Toaster/>
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
                    <Table.Cell>{aluno.media}</Table.Cell>
                    <Table.Cell class="text-right">
                    <Dialog.Root>
                        <DropdownMenu.Root>
                            <DropdownMenu.Trigger asChild let:builder>
                                <Button builders={[builder]} variant="outline">
                                    <ChevronDown/>
                                </Button>
                            </DropdownMenu.Trigger>
                            <DropdownMenu.Content class="w-56">
                                <DropdownMenu.Item>
                                    <a href="/alunos/{aluno.id}">Ver detalhes do aluno</a>
                                </DropdownMenu.Item>
                                <DropdownMenu.Item>
                                    <Dialog.Trigger>Excluir Aluno</Dialog.Trigger>
                                </DropdownMenu.Item>
                            </DropdownMenu.Content>
                            <Dialog.Content>
                                <Dialog.Header>
                                    <Dialog.Title>Você tem certeza que deseja exlcuir o aluno?</Dialog.Title>
                                    <Dialog.Description>
                                        Essa é uma ação permantente que quando executada não pode ser cancelada. Você tem certeza que deseja exluir o aluno?
                                    </Dialog.Description>
                                    <div class="w-full flex justify-center mx-auto p-6">
                                        <Dialog.Footer>
                                                <Button variant="destructive" on:click={async () => { deleteAluno(aluno.id); toast.info("Usuario excluido.") }} >
                                                    Sim eu quero excluir o aluno.
                                                </Button>
                                        </Dialog.Footer>
                                    </div>
                                </Dialog.Header>
                            </Dialog.Content>
                        </DropdownMenu.Root>
                    </Dialog.Root>
                </Table.Cell>
            </Table.Row>
        {/each}
    </Table.Body>
</Table.Root>
</div>
