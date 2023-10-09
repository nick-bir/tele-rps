<template>
    <div v-if="errorMessages" class="error-messages">
        <div
            v-for="message in errorMessages"
            :key="message"
            class="error-messages__error"
        >
            {{ message }}
        </div>
    </div>
</template>

<script setup lang="ts">
    import { useState } from '../../state/state';

    const { errorMessages } = useState();
</script>

<style>
    .error-messages {
        margin-bottom: 20px;
        color: red;
    }

    .error-messages__error {
        margin-bottom: 10px;
    }
</style>
