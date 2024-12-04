<script lang="ts">
    import { onMount } from "svelte";

    import { Button } from "$lib/components/ui/button";
    import { Checkbox } from "$lib/components/ui/checkbox";
    import { CirclePlus, ChevronDown, Edit } from "lucide-svelte";
    import { Separator } from "$lib/components/ui/separator/index.js";
    import * as Table from "$lib/components/ui/table";
    import * as DropdownMenu from "$lib/components/ui/dropdown-menu/index.js";
    import * as Dialog from "$lib/components/ui/dialog/index.js";
    import { page } from "$app/stores"
    import * as Form from "$lib/components/ui/form";
    import { toast } from "svelte-sonner";
    import { Toaster } from "$lib/components/ui/sonner";
    import { Input } from "$lib/components/ui/input/index.js";
    import { professorSchema, type ProfessorSchema } from "./schema";
    import * as Sheet from "$lib/components/ui/sheet";
    import type { PageData } from './$types';
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";

    import { type Professor, fetchProfessores } from "../utils";

    let { data }: { data: PageData } = $props();

    let professores: Professor[] = $state([])

    onMount(async () => {
        professores = await fetchProfessores("") ?? [];
    })

    async function deleteProfessor(id: number) {
        try {
            const url = "http://localhost:8080/api/professores/" + id;
            const response = await fetch(
               url, { method: "delete" } 
            );
            const code = response.status;
            if (code == 200) {
                toast.info("Professor excluido.");
            } else {
                console.log("deu pau");
            }
        } catch (err) {
            console.log(err);
        }
        professores = await fetchProfessores("") ?? [];
    }

   const form = superForm(data.form, {
        dataType: "json",
        validators: zodClient(professorSchema),
        onResult: async (result) => {
            console.log(result);
            if (result.result.type === "success") {
                professores = await fetchProfessores("") ?? [];
                toast.success("Professor Criado!")
            } else {
                toast.error("Erro enquanto criava professor")
            }
        }
    });

    const { form: formData } = form;

    let professorQuery = $state("");

    </script>

<div>
    <Toaster/>
    <div class="py-4 flex flex-col gap-4">
    <h1 class="text-2xl">Gerenciamento de Alunos</h1>
    <Button class="max-w-fit" href={$page.url + "/new"}>
        <CirclePlus class="mr-2" />
        Adicione um novo professor
    </Button>
    <Separator class="my-4" />
    <div class="flex w-full max-w-sm flex-col gap-1.5">
        <Input on:input={async () => { professores = await fetchProfessores(professorQuery) ?? [] }} 
            id="nome" placeholder="buscar professor" bind:value={professorQuery} />
    </div>
    <h2>Lista de Alunos</h2>
    <Table.Root>
        <Table.Header>
            <Table.Row>
                <Table.Head class="w-[100px]">Id</Table.Head>
                <Table.Head>Nome</Table.Head>
                <Table.Head>Email</Table.Head>
                <Table.Head>Idade</Table.Head>
                <Table.Head class="text-right"></Table.Head>
            </Table.Row>
        </Table.Header>
        <Table.Body>
            {#each professores as professor}
                <Table.Row>
                    <Table.Cell class="font-medium">{professor.id}</Table.Cell>
                    <Table.Cell>{professor.nome}</Table.Cell>
                    <Table.Cell>{professor.email}</Table.Cell>
                    <Table.Cell>{professor.idade}</Table.Cell>
                    <Table.Cell class="text-right">
                    <Sheet.Root>
                        <Sheet.Trigger><Button variant="secondary"><Edit/></Button></Sheet.Trigger>
                        <Sheet.Content>
                            <Sheet.Header>
                                <Sheet.Description>
                                <Sheet.Title>Editar disciplina</Sheet.Title>
                                    <form method="POST" use:form.enhance >
                                        <Form.Field {form} name="professorId">
                                            <Form.Control let:attrs>
                                                <span class="hidden">
                                                    {$formData.professorId = professor.id}
                                                </span>
                                                <Input type="hidden" {...attrs} bind:value={$formData.professorId} />
                                            </Form.Control>
                                        </Form.Field>
                                        <Form.Field {form} name="nome">
                                            <Form.Control let:attrs>
                                                <Form.Label>Nome</Form.Label>
                                                <span class="hidden">
                                                    {$formData.nome = professor.nome}
                                                </span>
                                                <Input {...attrs} bind:value={$formData.nome} />
                                            </Form.Control>
                                            <Form.Description>Nome do professor.</Form.Description>
                                            <Form.FieldErrors />
                                        </Form.Field>
                                        <Form.Field {form} name="email">
                                            <Form.Control let:attrs>
                                                <Form.Label>Email</Form.Label>
                                                <span class="hidden">
                                                    {$formData.email = professor.email}
                                                </span>
                                                <Input {...attrs} bind:value={$formData.email} />
                                            </Form.Control>
                                            <Form.Description>Email do professor.</Form.Description>
                                            <Form.FieldErrors />
                                        </Form.Field>
                                        <Form.Field {form} name="idade">
                                            <Form.Control let:attrs>
                                                <Form.Label>Idade</Form.Label>
                                                <span class="hidden">
                                                    {$formData.idade = professor.idade}
                                                </span>
                                                <Input {...attrs} type="number" bind:value={$formData.idade} />
                                            </Form.Control>
                                            <Form.Description>Idade do professor.</Form.Description>
                                            <Form.FieldErrors />
                                        </Form.Field>
                                        <Form.Button>Atualizar professor.</Form.Button>
                                    </form>                                
                                <div class="pt-4">
                                <Dialog.Root>
                                    <Dialog.Trigger><Button variant="destructive"> Excluir Professor</Button></Dialog.Trigger>
                                    <Dialog.Content>
                                        <Dialog.Header>
                                            <Dialog.Title>Você tem certeza que deseja excluir o professor?</Dialog.Title>
                                            <Dialog.Description>
                                                Essa é uma ação permantente que quando executada não pode ser cancelada. Você tem certeza que deseja exluir o aluno?
                                            </Dialog.Description>
                                            <div class="w-full flex justify-center mx-auto p-6">
                                                <Dialog.Footer>
                                                        <Dialog.Close>
                                                            <Button variant="destructive" on:click={async () => { deleteProfessor(professor.id) }} >
                                                                Sim eu quero excluir o professor.
                                                            </Button>
                                                        </Dialog.Close>
                                                </Dialog.Footer>
                                            </div>
                                        </Dialog.Header>
                                    </Dialog.Content>
                                </Dialog.Root>
                                </div>
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
