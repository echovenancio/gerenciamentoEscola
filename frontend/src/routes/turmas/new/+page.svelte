<script lang="ts">
    import * as Form from "$lib/components/ui/form";
    import { Input } from "$lib/components/ui/input";
    import { toast } from "svelte-sonner";
    import { Toaster } from "$lib/components/ui/sonner";
    import { turmaSchema, type TurmaSchema } from "./schema";
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";
    import type { PageData } from "./$types";

    let { data }: { data: PageData } = $props();

    const form = superForm(data.form, {
        dataType: "json",
        validators: zodClient(turmaSchema),
        onResult: (result) => {
            console.log(result);
            if (result.result.type === "success") {
                toast.success("Turma Criada!")
            } else {
                toast.error("Erro enquanto criava turma")
            }
        }
    });

    const { form: formData } = form;

</script>

<div>
    <Toaster/>
    <form method="POST" use:form.enhance >
        <Form.Field {form} name="identificador">
            <Form.Control let:attrs>
                <Form.Label>Identificador</Form.Label>
                <Input {...attrs} bind:value={$formData.identificador} />
            </Form.Control>
            <Form.Description>Identificador da turma.</Form.Description>
            <Form.FieldErrors />
        </Form.Field>
        <Form.Button>Cadastrar turma.</Form.Button>
    </form>
</div>
