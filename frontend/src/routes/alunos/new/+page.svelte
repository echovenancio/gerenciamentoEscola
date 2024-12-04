<script lang="ts">
    import * as Form from "$lib/components/ui/form";
    import { Input } from "$lib/components/ui/input";
    import { Toaster } from "$lib/components/ui/sonner";
    import { alunoSchema, type AlunoSchema } from "./schema";
    import { toast } from "svelte-sonner";
    import type { PageData } from './$types';
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";

    export let data: SuperValidated<Infer<AlunoSchema>>;

    const form = superForm(data, {
        dataType: "json",
        validators: zodClient(alunoSchema),
        onResult: (result) => {
            console.log(result);
            if (result.result.type === "success") {
                toast.success("Aluno Criado!")
            } else {
                toast.error("Erro enquanto criava aluno")
            }
        }
    });

    const { form: formData, enhance } = form;
</script>
 
<div>
    <Toaster/>


    <form method="POST" use:enhance >
        <Form.Field {form} name="matricula">
            <Form.Control let:attrs>
                <Form.Label>Matricula</Form.Label>
                <Input {...attrs} bind:value={$formData.matricula} />
            </Form.Control>
            <Form.Description>Número da matricula.</Form.Description>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="nome">
            <Form.Control let:attrs>
                <Form.Label>Nome</Form.Label>
                <Input {...attrs} bind:value={$formData.nome} />
            </Form.Control>
            <Form.Description>Nome do aluno.</Form.Description>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="email">
            <Form.Control let:attrs>
                <Form.Label>Email</Form.Label>
                <Input {...attrs} bind:value={$formData.email} />
            </Form.Control>
            <Form.Description>Email do aluno.</Form.Description>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="idade">
            <Form.Control let:attrs>
                <Form.Label>Idade</Form.Label>
                <Input {...attrs} type="number" bind:value={$formData.idade} />
            </Form.Control>
            <Form.Description>Idade do aluno.</Form.Description>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Button >Cadastrar aluno.</Form.Button>
    </form>
</div>
