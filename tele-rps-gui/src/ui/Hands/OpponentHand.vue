<template>
    <div>[opponent hand: {{ choise }}]</div>
</template>

<script setup lang="ts">
    import { Figures } from '../../logic/api';

    defineProps<{ choise: Figures }>();
</script>
