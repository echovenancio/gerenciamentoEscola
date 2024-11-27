<script lang="ts">
    import * as Form from "$lib/components/ui/form";
    import { Input } from "$lib/components/ui/input";
    import { turmaSchema, type TurmaSchema } from "./schema";
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";

    export let data: SuperValidated<Infer<TurmaSchema>>;

    const form = superForm(data, {
        dataType: "json",
        validators: zodClient(turmaSchema),
    });

    const { form: formData, enhance } = form;

</script>

 
<form method="POST" use:enhance >
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
