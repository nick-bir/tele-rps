<template>
    <div class="hand">
        <Subheader>{{ name }}</Subheader>
        <div class="weapon">{{ weapon }}</div>
    </div>
</template>
<script setup lang="ts">
    import Subheader from '../Subheader.vue';
    import { computed, defineProps } from 'vue';
    import { Figures } from '../../../logic/api';

    const props = defineProps<{ choise: Figures; name: string }>();

    const weapon = computed(() => {
        switch (props.choise) {
            case 'choosing':
                return '🤔';
            case 'rock':
                return '🎸';
            case 'paper':
                return '📃';
            case 'scissors':
                return '✂️';
            default:
                return '' as never;
        }
    });
</script>

<style scoped>
    .hand {
        display: inline-flex;
        flex-direction: column;
        align-items: center;
        width: 100px;
        font-size: 5rem;
        margin-bottom: 1rem;
        border: 1px solid silver;
        border-radius: 10px;
        padding: 10px;
    }

    .weapon {
        font-size: 3rem;
    }
</style>
