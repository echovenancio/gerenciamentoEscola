<script lang="ts">
    import * as Form from "$lib/components/ui/form";
    import { Input } from "$lib/components/ui/input";
    import { toast } from "svelte-sonner";
    import { Toaster } from "$lib/components/ui/sonner";
    import { disciplinaSchema, type DisciplinaSchema } from "./schema";
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";

    export let data: SuperValidated<Infer<DisciplinaSchema>>;

    const form = superForm(data, {
        dataType: "json",
        validators: zodClient(disciplinaSchema),
        onResult: (result) => {
            console.log(result);
            if (result.result.type === "success") {
                toast.success("Disciplina Criada!")
            } else {
                toast.error("Erro enquanto criava disciplina")
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
            <Form.Description>Nome da disciplina.</Form.Description>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Button>Cadastrar Disciplina.</Form.Button>
    </form>
</div>
