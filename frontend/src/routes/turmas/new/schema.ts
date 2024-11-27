import { z } from "zod";

export const turmaSchema = z.object({
    identificador: z.string().min(3),
});

export type TurmaSchema = z.infer<typeof turmaSchema>;
