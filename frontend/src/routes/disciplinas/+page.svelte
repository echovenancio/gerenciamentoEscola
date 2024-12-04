<script lang="ts">
    import { onMount } from "svelte";
    import * as Sheet from "$lib/components/ui/sheet";
    import { Button } from "$lib/components/ui/button";
    import { ChevronDown, CirclePlus, Edit } from "lucide-svelte";
    import { Separator } from "$lib/components/ui/separator/index.js";
    import { type Disciplina, fetchDisciplinas } from "../utils";
    import * as Table from "$lib/components/ui/table";
    import { page } from "$app/stores";
    import { Input } from "$lib/components/ui/input";
    import * as Dialog from "$lib/components/ui/dialog/index.js";
    import { Toaster } from "$lib/components/ui/sonner";
    import * as Form from "$lib/components/ui/form";
    import { toast } from "svelte-sonner";
    import { disciplinaSchema, type DisciplinaSchema } from "./schema.ts" 
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";
    import type { PageData } from "./$types";

    let { data }: { data: PageData } = $props();

    let disciplinas: Disciplina[] = $state([])

    let disciplinaQuery = $state("")

    const form = superForm(data.form, {
        dataType: "json",
        validators: zodClient(disciplinaSchema),
        onResult: async (result) => {
            console.log(result);
            if (result.result.type === "success") {
                disciplinas = await fetchDisciplinas("") ?? [];
                toast.success("Disciplina atualizada!")
            } else {
                toast.error("Erro enquanto atualizava disciplina")
            }
        }
    });

    const { form: formData } = form;

    onMount(async () => {
        disciplinas = await fetchDisciplinas("") ?? [];
    })

    async function deleteDisciplina(id: number) {
        try {
            const url = "http://localhost:8080/api/disciplinas/" + id;
            const response = await fetch(
               url, { method: "delete" } 
            );
            const code = response.status;
            if (code == 200) {
                toast.info("Aluno excluido.") 
            } else {
                toast.info("Erro enquanto excluia aluno.") 
            }
        } catch (err) {
            console.log(err);
        }
        disciplinas = await fetchDisciplinas("") ?? [];
    }
</script>

<div>
    <Toaster/>
    <div class="py-4 flex flex-col gap-4">
        <h1 class="text-2xl">Gerenciamento de Disciplinas</h1>
        <Button class="max-w-fit" href={$page.url + "/new"}>
            <CirclePlus class="mr-2" />
            Adicione uma nova disciplina
        </Button>
        <Separator class="my-4" />
        <div class="flex w-full max-w-sm flex-col gap-1.5">
            <Input on:input={async () => { disciplinas = await fetchDisciplinas(disciplinaQuery) ?? [] }} 
                id="nome" placeholder="buscar disciplina" bind:value={disciplinaQuery} />
        </div>
        <h2>Lista de Alunos</h2>
        <Table.Root>
            <Table.Header>
                <Table.Row>
                    <Table.Head class="w-[100px]">Id</Table.Head>
                    <Table.Head class="text-right">Nome</Table.Head>
                    <Table.Head class="text-right"></Table.Head>
                </Table.Row>
            </Table.Header>
            <Table.Body>
                {#each disciplinas as disciplina}
                    <Table.Row>
                        <Table.Cell class="font-medium">{disciplina.id}</Table.Cell>
                        <Table.Cell class="text-right">{disciplina.nome}</Table.Cell>
                        <Table.Cell class="text-right">
                        <Sheet.Root>
                            <Sheet.Trigger><Button variant="secondary"><Edit/></Button></Sheet.Trigger>
                            <Sheet.Content>
                                <Sheet.Header>
                                    <Sheet.Title>Editar disciplina</Sheet.Title>
                                    <form method="POST" use:form.enhance >
                                        <Form.Field {form} name="nome">
                                            <Form.Control let:attrs>
                                                <span class="hidden">
                                                    {$formData.disciplinaId = disciplina.id}
                                                </span>
                                                <Input type="hidden" hidden {...attrs} bind:value={$formData.disciplinaId} />
                                            </Form.Control>
                                        </Form.Field>
                                        <Form.Field {form} name="nome">
                                            <Form.Control let:attrs>
                                                <Form.Label>Nome</Form.Label>
                                                <span class="hidden">
                                                    {$formData.nome = disciplina.nome}
                                                </span>
                                                <Input {...attrs} bind:value={$formData.nome} />
                                            </Form.Control>
                                            <Form.Description>Nome da disciplina.</Form.Description>
                                            <Form.FieldErrors />
                                        </Form.Field>
                                        <Form.Button>Atualizar disciplina.</Form.Button>
                                    </form>
                                    <Sheet.Description>
                                    <Dialog.Root>
                                        <Dialog.Trigger><Button variant="destructive"> Excluir Disciplina</Button></Dialog.Trigger>
                                        <Dialog.Content>
                                            <Dialog.Header>
                                                <Dialog.Title>Você tem certeza que deseja excluir a disciplina?</Dialog.Title>
                                                <Dialog.Description>
                                                    Essa é uma ação permantente que quando executada não pode ser cancelada. Você tem certeza que deseja exluir o aluno?
                                                </Dialog.Description>
                                                <div class="w-full flex justify-center mx-auto p-6">
                                                    <Dialog.Footer>
                                                            <Dialog.Close>
                                                                <Button variant="destructive" on:click={async () => { deleteDisciplina(disciplina.id) }} >
                                                                    Sim eu quero excluir a disciplina.
                                                                </Button>
                                                            </Dialog.Close>
                                                    </Dialog.Footer>
                                                </div>
                                            </Dialog.Header>
                                        </Dialog.Content>
                                    </Dialog.Root>
                                    </Sheet.Description>
                                </Sheet.Header>
                                </Sheet.Content>
                            </Sheet.Root>
                        </Table.Cell>
                    </Table.Row>
                {/each}
            </Table.Body>
        </Table.Root>
    </div>
</div>
