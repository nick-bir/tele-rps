<template>
    <div
        class="weapon"
        :class="{
            [`weapon_size_${size || 'md'}`]: true,
        }"
    >
        {{ icon }}
    </div>
</template>

<script setup lang="ts">
    import { computed, defineProps } from 'vue';
    import { Gesture } from '../state';

    const props = defineProps<{
        size?: 'sm' | 'md' | 'lg';
        type: Gesture;
    }>();

    const icon = computed(() => {
        switch (props.type) {
            case 'PENDING':
                return '🤔';
            case 'ROCK':
                return '🪨';
            case 'PAPER':
                return '📃';
            case 'SCISSORS':
                return '✂️';
            default:
                return '' as never;
        }
    });
</script>

<style scoped>
    .weapon_size_md {
        font-size: 3rem;
    }
    .weapon_size_lg {
        font-size: 5rem;
    }

    .weapon {
        border: 1px solid silver;
        border-radius: 10px;
        padding: 20px;
    }
</style>
