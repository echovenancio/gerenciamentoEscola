<script lang="ts">
    import { onMount } from "svelte";
    import { page } from "$app/stores";
    import { Separator } from "$lib/components/ui/separator";
    import { Edit } from "lucide-svelte";
    import { type Aluno, type Disciplina, type DisciplinaAluno, fetchAluno, fetchDisciplinas, fetchProfessores, fetchTurmas, type Professor, type Turma } from "../../utils";
    import { goto } from "$app/navigation";
    import * as Sheet from "$lib/components/ui/sheet";
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";
    import * as Form from "$lib/components/ui/form";
    import { Input } from "$lib/components/ui/input";
    import { Toaster } from "$lib/components/ui/sonner";
    import { addAlunoTurma, alunoDisciplinaSchema, alunoEditSchema, notaSchema, type AlunoDisciplinaSchema } from "./schema";
    import { toast } from "svelte-sonner";
    import * as Select from "$lib/components/ui/select/index.js";
    import { z } from "zod";
    import type { PageData } from "./$types";
    import { Button } from "$lib/components/ui/button";
    import { Checkbox } from "$lib/components/ui/checkbox";
    import * as Tabs from "$lib/components/ui/tabs";

    let { data }: { data: PageData } = $props();

    const id = data.id
    let aluno: Partial<Aluno> = $state({});
    let professores: Professor[] = $state([]);
    let disciplinas: Disciplina[] = $state([]);
    let turmas: Turma[] = $state([]);
    let media = $derived(aluno.media?.toFixed(1));

    const disciplinaForm = superForm(data.disciplinaForm, {
        dataType: "json",
        validators: zodClient(alunoDisciplinaSchema),
        onResult: (result) => {
            console.log(result);
            if (result.result.type === "success") {
                fetchAluno(Number.parseInt(id)).then((r) => {
                    console.log(r)
                    aluno = r ?? {}
                }).catch((e) => {
                    console.log(e)
                });
                toast.success("Disciplina adicionada!")
            } else {
                toast.error("Erro enquanto adicionava disciplina")
                }
            }})

    const editAlunoDisciplinaForm = superForm(data.notaForm, {
        dataType: "json",
        validators: zodClient(notaSchema),
        onResult: (result) => {
            console.log(result);
            if (result.result.type === "success") {
                fetchAluno(Number.parseInt(id)).then((r) => {
                    console.log(r)
                    aluno = r ?? {}
                }).catch((e) => {
                    console.log(e)
                });
                toast.success("Disciplina Editada!")
            } else {
                toast.error("Erro enquanto editava disciplina")
                }
            }})

    const editAlunoForm = superForm(data.alunoEditForm, {
        dataType: "json",
        validators: zodClient(alunoEditSchema),
        onResult: (result) => {
            console.log(result);
            if (result.result.type === "success") {
                fetchAluno(Number.parseInt(id)).then((r) => {
                    console.log(r)
                    aluno = r ?? {}
                }).catch((e) => {
                    console.log(e)
                });
                toast.success("Aluno atualizado!")
            } else {
                toast.error("Erro enquanto editava aluno")
                }
            }})

    const addAlunoTurmaForm = superForm(data.addAlunoTurmaForm, {
        dataType: "json",
        validators: zodClient(addAlunoTurma),
        onResult: (result) => {
            console.log(result);
            if (result.result.type === "success") {
                fetchAluno(Number.parseInt(id)).then((r) => {
                    console.log(r)
                    aluno = r ?? {}
                }).catch((e) => {
                    console.log(e)
                });
                toast.success("Aluno registrado na turma!")
            } else {
                toast.error("Erro enquanto registrava aluno na turma")
                }
            }})

    const { form: disciplinaFormData } = disciplinaForm;
    const { form: editAlunoDisciplinaFormData } = editAlunoDisciplinaForm;
    const { form: editAlunoFormData } = editAlunoForm;
    const { form: addAlunoTurmaFormData } = addAlunoTurmaForm;

    onMount(async () => {
        var ret = await fetchAluno(Number.parseInt(id));
        disciplinas = await fetchDisciplinas("") ?? [];
        professores = await fetchProfessores("") ?? [];
        turmas = await fetchTurmas("") ?? [];
        console.log(ret);
        if (!ret) {
            goto("/404", { replaceState: true}); 
        } else {
            aluno = ret;
            {
                $editAlunoFormData.nome = aluno.nome;
                $editAlunoFormData.email = aluno.email;
                $editAlunoFormData.idade = aluno.idade;
                if (aluno.turma != null) {
                    $addAlunoTurmaFormData.turmaId = aluno.turma.id;
                }
                $addAlunoTurmaFormData.alunoId = aluno.id;
            };
        }
    })

</script>

<div>
<Toaster/>
<div class="py-4 flex flex-col gap-4">
    <div class="flex justify-between">
        <div>
            <div class="flex items-center flex-row gap-2">
                <Sheet.Root>
                    <Sheet.Trigger>
                        <Button variant="secondary"><Edit/></Button>
                    </Sheet.Trigger>
                    <Sheet.Content>
                        <Sheet.Header>
                            <Sheet.Title>Editar Aluno</Sheet.Title>
                            <Sheet.Description>
                                <form method="POST" action="?/editAluno" use:editAlunoForm.enhance >
                                    <Form.Field form={editAlunoForm} name="nome">
                                        <Form.Control let:attrs>
                                            <Form.Label>Nome</Form.Label>
                                            <Input {...attrs} bind:value={$editAlunoFormData.nome} name={attrs.nome} />
                                        </Form.Control>
                                        <Form.Description>Nota do aluno.</Form.Description>
                                        <Form.FieldErrors />
                                    </Form.Field>
                                    <Form.Field form={editAlunoForm} name="email">
                                        <Form.Control let:attrs>
                                            <Form.Label>Email</Form.Label>
                                            <Input {...attrs} bind:value={$editAlunoFormData.email} name={attrs.email} />
                                        </Form.Control>
                                        <Form.Description>Email do aluno.</Form.Description>
                                        <Form.FieldErrors />
                                    </Form.Field>
                                    <Form.Field form={editAlunoForm} name="idade">
                                        <Form.Control let:attrs>
                                            <Form.Label>Idade</Form.Label>
                                            <Input type="number" {...attrs} bind:value={$editAlunoFormData.idade} name={attrs.idade} />
                                        </Form.Control>
                                        <Form.Description>Idade do aluno.</Form.Description>
                                        <Form.FieldErrors />
                                    </Form.Field>
                                    <Form.Button>Editar aluno.</Form.Button>
                                </form>
                                <form class="pt-4" method="POST" action="?/addToTurma" use:addAlunoTurmaForm.enhance >
                                    <Form.Field form={addAlunoTurmaForm} name="alunoId">
                                        <Form.Control let:attrs>
                                                <span class="hidden">{$addAlunoTurmaFormData.alunoId = Number.parseInt(id)}</span>
                                            <input hidden bind:value={$addAlunoTurmaFormData.alunoId} name={attrs.alunoId} />
                                        </Form.Control>
                                    </Form.Field>
                                    <Form.Field form={addAlunoTurmaForm} name="turmaId">
                                        <Form.Control let:attrs>
                                            <Form.Label>Turma</Form.Label>
                                            <Select.Root
                                            onSelectedChange={(v) => {
                                              v && ($addAlunoTurmaFormData.turmaId = Number.parseInt(v.value));
                                            }}
                                          >
                                            <Select.Trigger {...attrs}>
                                                <Select.Value placeholder="Selecione a turma." />
                                            </Select.Trigger>
                                            <Select.Content>
                                                {#each turmas as t}
                                                    <Select.Item value={t.id} label={t.identificador} />
                                                {/each}
                                            </Select.Content>
                                          </Select.Root>
                                          <input hidden bind:value={$addAlunoTurmaFormData.turmaId} name={attrs.turmaId} />
                                        </Form.Control>
                                        <Form.Description>Turma que o aluno será adicionado.</Form.Description>
                                        <Form.FieldErrors />
                                    </Form.Field>
                                    <Form.Button >Adicionar aluno a turma.</Form.Button>
                                </form>
                            </Sheet.Description>
                        </Sheet.Header>
                    </Sheet.Content>
                </Sheet.Root>
                <h1 class="text-5xl uppercase">{aluno.nome}</h1>
            </div>
            <p class="text-muted-foreground">Email: {aluno.email}</p>
            <p class="text-muted-foreground text-sm">Matricula: {aluno.matricula}</p>
            <p class="text-muted-foreground text-sm">Idade: {aluno.idade}</p>
            {#if aluno.turma != null}
                <h2 class="text-xl">Turma: <span class="uppercase">{aluno.turma.identificador}</span></h2>
            {/if}
        </div>
        <p class="flex flex-col">
            <span 
                class="text-5xl"
                class:text-red-400={aluno.media <= 5.0}
                class:text-green-600={aluno.media >= 6.0}
                >
                {media}
            </span>
            <span class="text-muted-foreground text-sm">Media</span>
        </p>
    </div>
    <Separator/>
    <Sheet.Root>
        <Sheet.Trigger>Adicionar Disciplina</Sheet.Trigger>
        <Sheet.Content>
            <Sheet.Header>
                <Sheet.Title>Adicionar Disciplina</Sheet.Title>
                <Sheet.Description>
                    <form method="POST" action="?/addDisciplina" use:disciplinaForm.enhance >
                        <Form.Field form={disciplinaForm} name="disciplinaId">
                            <Form.Control let:attrs>
                                <Form.Label>Disciplina</Form.Label>
                                <Select.Root
                                onSelectedChange={(v) => {
                                  v && ($disciplinaFormData.disciplinaId = v.value);
                                }}
                              >
                                <Select.Trigger {...attrs}>
                                    <Select.Value placeholder="Selecione a disciplina" />
                                </Select.Trigger>
                                <Select.Content>
                                    {#each disciplinas as d}
                                        <Select.Item value={d.id} label={d.nome} />
                                    {/each}
                                </Select.Content>
                              </Select.Root>
                              <input hidden bind:value={$disciplinaFormData.disciplinaId} name={attrs.disciplinaId} />
                            </Form.Control>
                            <Form.Description>Disciplina a ser adicionada.</Form.Description>
                            <Form.FieldErrors />
                        </Form.Field>
                        <Form.Field form={disciplinaForm} name="professorId">
                            <Form.Control let:attrs>
                                <Form.Label>Disciplina</Form.Label>
                                <Select.Root
                                onSelectedChange={(v) => {
                                  v && ($disciplinaFormData.professorId = v.value);
                                }}
                              >
                                <Select.Trigger {...attrs}>
                                    <Select.Value placeholder="Selecione o professor." />
                                </Select.Trigger>
                                <Select.Content>
                                    {#each professores as p}
                                        <Select.Item value={p.id} label={p.nome} />
                                    {/each}
                                </Select.Content>
                              </Select.Root>
                              <input hidden bind:value={$disciplinaFormData.professorId} name={attrs.professorId} />
                            </Form.Control>
                            <Form.Description>Professor a ser adicionada.</Form.Description>
                            <Form.FieldErrors />
                        </Form.Field>
                        <Form.Button >Cadastrar aluno.</Form.Button>
                    </form>
                </Sheet.Description>
            </Sheet.Header>
        </Sheet.Content>
    </Sheet.Root>
    <Separator/>
    <h2 class="text-xl uppercase">Disciplinas</h2>
        <Tabs.Root value="em_curso" class="">
            <Tabs.List>
                <Tabs.Trigger value="em_curso">Em curso</Tabs.Trigger>
                <Tabs.Trigger value="finalizadas">Finalizada</Tabs.Trigger>
            </Tabs.List>
            <Tabs.Content value="em_curso">
                <div class="flex gap-4 flex-col">
                    {#each aluno.disciplinas ?? [] as disciplina}
                        {#if disciplina.status == "em curso"}
                        <div class="flex justify-between items-center">
                            <div class="flex justify-between gap-4 items-center">
                                <Sheet.Root>
                                    <Sheet.Trigger><Button variant="secondary"><Edit/></Button></Sheet.Trigger>
                                    <Sheet.Content>
                                        <Sheet.Header>
                                            <Sheet.Title>Editar disciplina</Sheet.Title>
                                            <Sheet.Description>
                                                <form method="POST" action="?/setNota" use:editAlunoDisciplinaForm.enhance >
                                                    <Form.Field form={editAlunoDisciplinaForm} name="disciplinaId">
                                                        <Form.Control let:attrs>
                                                            <span class="hidden">{$editAlunoDisciplinaFormData.disciplinaId = disciplina.id}</span>
                                                            <Input {...attrs} type="hidden" bind:value={editAlunoDisciplinaFormData.nota} name={attrs.disciplinaId} />
                                                        </Form.Control>
                                                    </Form.Field>
                                                    <Form.Field form={editAlunoDisciplinaForm} name="nota">
                                                        <Form.Control let:attrs>
                                                            <span class="hidden">{$editAlunoDisciplinaFormData.nota = disciplina.nota}</span>
                                                            <Form.Label>Nota</Form.Label>
                                                            <Input type="number" step="any" {...attrs} bind:value={$editAlunoDisciplinaFormData.nota} name={attrs.nota} />
                                                        </Form.Control>
                                                        <Form.Description>Nota do aluno na disciplina.</Form.Description>
                                                        <Form.FieldErrors />
                                                    </Form.Field>
                                                    <Form.Field form={editAlunoDisciplinaForm} name="finalizar">
                                                        <Form.Control let:attrs>
                                                            <Checkbox {...attrs} bind:checked={$editAlunoDisciplinaFormData.finalizar} />
                                                            <Form.Label>Finalizar Disciplina</Form.Label>
                                                            <input bind:value={$editAlunoDisciplinaFormData.finalizar} name={attrs.finalizar} hidden />
                                                        </Form.Control>
                                                        <Form.FieldErrors />
                                                    </Form.Field>
                                                    <Form.Button>Atualizar disciplina.</Form.Button>
                                                </form>
                                            </Sheet.Description>
                                        </Sheet.Header>
                                    </Sheet.Content>
                                </Sheet.Root>
                                <span class="text-xl">
                                    {disciplina.nome}
                                </span>
                            </div>
                            <span 
                                class="text-xl"
                                class:text-red-400={disciplina.nota <= 5.0}
                                class:text-green-600={disciplina.nota >= 6.0}
                                >
                                {disciplina.nota.toFixed(1)}
                            </span>
                        </div>
                    {/if}
                    {/each}
                </div>
            </Tabs.Content>
            <Tabs.Content value="finalizadas">
               <div class="flex gap-4 flex-col">
                    {#each aluno.disciplinas ?? [] as disciplina}
                        {#if disciplina.status != "em curso"}
                        <div class="flex justify-between items-center">
                            <div class="flex justify-between gap-4 items-center">
                                {#if disciplina.status == "concluido"}
                                   <span class="text-green-400 text-xl">APROVADO</span> 
                                {:else}
                                   <span class="text-red-400 text-xl">REPROVADO</span> 
                                {/if}
                                <span class="text-xl">
                                    {disciplina.nome}
                                </span>
                            </div>
                            <span 
                                class="text-xl"
                                class:text-red-400={disciplina.nota <= 5.0}
                                class:text-green-600={disciplina.nota >= 6.0}
                                >
                                {disciplina.nota.toFixed(1)}
                            </span>
                        </div>
                    {/if}
                    {/each}
                </div>

            </Tabs.Content>
        </Tabs.Root>
</div>
</div>
