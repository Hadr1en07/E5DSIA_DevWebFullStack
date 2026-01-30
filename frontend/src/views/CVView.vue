<script setup lang="ts">
const pdfUrl = '/cv.pdf'
</script>

<template>
  <section class="card" style="padding: 18px;">
    <h1 style="margin: 0 0 10px;">CV</h1>
    <p style="margin: 0 0 14px; color: var(--muted);">
      Si le PDF ne s’affiche pas, vous pouvez le télécharger :
      <a :href="pdfUrl" target="_blank" rel="noreferrer">ouvrir le CV</a>.
    </p>

    <div class="frame">
      <iframe :src="pdfUrl" title="CV" />
    </div>
  </section>
</template>

<style scoped>
.frame {
  width: 100%;
  height: 78vh;
  border: 1px solid var(--border);
  border-radius: 14px;
  overflow: hidden;
  background: rgba(255,255,255,0.03);
}
iframe {
  width: 100%;
  height: 100%;
  border: 0;
}
</style>
