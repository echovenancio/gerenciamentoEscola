import { z } from "zod";

export const turmaSchema = z.object({
    turmaId: z.number(),
    identificador: z.string(),
});

export type TurmaSchema = z.infer<typeof turmaSchema>;
