<script lang="ts">
    import '../app.css';
    import { ModeWatcher } from 'mode-watcher';
    import { School } from 'lucide-svelte';
    import Sun from "lucide-svelte/icons/sun";
    import Moon from "lucide-svelte/icons/moon";
    import { toggleMode } from "mode-watcher";
    import { Button } from '$lib/components/ui/button';
    let { children } = $props();
    let nav_links = [
        { name: "Aluno", href: "/alunos" },
        { name: "Turmas", href: "/turmas" },
        { name: "Disciplinas", href: "/disciplinas" },
    ];
</script>

<ModeWatcher/>
<header class="container sticky">
    <div class="flex items-center justify-between py-4 border-b-2">
    <span>
            <Button variant="outline" href="/">
                <School/>
            </Button>
    </span>
    <nav>
        <ul class="flex justify-evenly gap-4">
            {#each nav_links as link }
                <li>
                    <Button 
                        variant="ghost"
                        href={link.href}
                    >{link.name}</Button>
                </li>
            {/each}
        </ul>
    </nav>
    <Button on:click={toggleMode} variant="outline" size="icon">
  <Sun
    class="h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0"
  />
  <Moon
    class="absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100"
  />
  <span class="sr-only">Toggle theme</span>
</Button>
    </div>
</header>

<main class="container mx-auto lg:max-w-4xl">
    {@render children()}
</main>

<style>
</style>
