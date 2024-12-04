export type Professor = {
    id: number,
    nome: string,
    email: string,
    idade: number
}

export type Disciplina = {
    id: number,
    nome: string
}

export type Turma = {
    id: number,
    identificador: string
}

export type DisciplinaAluno = {
    id: number,
    nome: string,
    nota: number,
    status: string
}

export type Aluno = {
    id: number,
    nome: string,
    email: string,
    matricula: string,
    media: number,
    idade: number,
    turma: Turma | null,
    disciplinas: DisciplinaAluno[] | undefined,
}

export async function fetchDisciplinas(nome: string): Promise<Disciplina[] | null> {
    try {
        var response = null;
        if (nome === "") {
            response = await fetch("http://localhost:8080/api/disciplinas");
        } else {
            response = await fetch("http://localhost:8080/api/disciplinas?nome="+nome);
        }
        if (!response.ok) throw new Error("Errro buscando disciplinas");
        return await response.json();
    } catch (err) {
        console.log(err);
        return null;
    }
}

export async function fetchAlunos(nome: string): Promise<Aluno[] | null>  {
    try {
        var response = null;
        if (nome === "") {
            response = await fetch("http://localhost:8080/api/alunos");
        } else {
            response = await fetch("http://localhost:8080/api/alunos?nome=" + nome);
        }
        if (!response.ok) throw new Error("Errro buscando alunos");
        return response.json();
    } catch (err) {
        console.log(err);
        return null
    }
}

export async function fetchTurmas(identificador: string): Promise<Turma[] | null> {
    try {
        var response = null;
        if (identificador === "") {
            response = await fetch("http://localhost:8080/api/turmas");
        } else {
            response = await fetch("http://localhost:8080/api/turmas?identificador="+identificador);
            console.log(response)
        }
        if (!response.ok) throw new Error("Errro buscando turmas");
        return await response.json();
    } catch (err) {
        console.log(err);
        return null;
    }
}

export async function fetchProfessores(nome: string): Promise<Professor[] | null>  {
    try {
        var response = null;
        if (nome === "") {
            response = await fetch("http://localhost:8080/api/professores");
        } else {
            response = await fetch("http://localhost:8080/api/professores?nome="+nome);
        }
        if (!response.ok) throw new Error("Errro buscando professores");
        return response.json();
    } catch (err) {
        console.log(err);
        return null;
    }
}

export async function fetchAluno(id: number): Promise<Aluno | null> {
    console.log("rodadndo aaqui")
    try {
        let response = await fetch("http://localhost:8080/api/alunos/" + id);
        if (!response.ok) throw new Error("error fetching aluno");
        let data = await response.json();
        console.log(data);
        return data
    } catch (err) {
        console.log("deu pau man")
        console.log(err);
        return null
    }
}
