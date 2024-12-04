<script lang="ts">
    import * as Form from "$lib/components/ui/form";
    import { Input } from "$lib/components/ui/input";
    import { Toaster } from "$lib/components/ui/sonner";
    import { toast } from "svelte-sonner";
    import { professorSchema, type ProfessorSchema } from "./schema";
    import type { PageData } from './$types';
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";

    export let data: SuperValidated<Infer<ProfessorSchema>>;

    const form = superForm(data, {
        dataType: "json",
        validators: zodClient(professorSchema),
        onResult: (result) => {
            console.log(result);
            if (result.result.type === "success") {
                toast.success("Professor Criado!")
            } else {
                toast.error("Erro enquanto criava professor")
            }
        }
    });

    const { form: formData, enhance } = form;
</script>
 
<div>
    <Toaster/>
    <form method="POST" use:enhance >
        <Form.Field {form} name="nome">
            <Form.Control let:attrs>
                <Form.Label>Nome</Form.Label>
                <Input {...attrs} bind:value={$formData.nome} />
            </Form.Control>
            <Form.Description>Nome do professor.</Form.Description>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="email">
            <Form.Control let:attrs>
                <Form.Label>Email</Form.Label>
                <Input {...attrs} bind:value={$formData.email} />
            </Form.Control>
            <Form.Description>Email do professor.</Form.Description>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="idade">
            <Form.Control let:attrs>
                <Form.Label>Idade</Form.Label>
                <Input {...attrs} type="number" bind:value={$formData.idade} />
            </Form.Control>
            <Form.Description>Idade do professor.</Form.Description>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Button>Cadastrar professor.</Form.Button>
    </form>
</div>
