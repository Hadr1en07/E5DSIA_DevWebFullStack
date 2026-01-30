<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useBooksStore } from '@/stores/book'
import { BookCategory, BookCategoryLabels } from '@/types'

const booksStore = useBooksStore()

const categories = computed(() => Object.values(BookCategory))

onMounted(async () => {
  if (booksStore.books.length === 0) {
    await booksStore.fetchBooks()
  }
})

function setSearch(v: string) {
  booksStore.setSearchQuery(v)
}

function setCategory(v: string) {
  booksStore.setCategory(v ? (v as BookCategory) : null)
}
</script>

<template>
  <section class="head">
    <div>
      <h1 style="margin:0;">Catalogue</h1>
      <p class="muted" style="margin:6px 0 0;">
        Parcours et filtre les livres (public). L’admin peut créer/modifier/supprimer.
      </p>
    </div>

    <div class="filters card">
      <input
        class="input"
        type="text"
        placeholder="Rechercher (titre ou auteur)…"
        :value="booksStore.searchQuery"
        @input="setSearch(($event.target as HTMLInputElement).value)"
      />

      <select class="input" :value="booksStore.selectedCategory ?? ''" @change="setCategory(($event.target as HTMLSelectElement).value)">
        <option value="">Toutes les catégories</option>
        <option v-for="c in categories" :key="c" :value="c">
          {{ BookCategoryLabels[c] }}
        </option>
      </select>
    </div>
  </section>

  <p v-if="booksStore.loading">Chargement…</p>
  <p v-else-if="booksStore.error" class="muted">{{ booksStore.error }}</p>

  <div v-else class="grid">
    <article v-for="b in booksStore.filteredBooks" :key="b.id" class="card book">
      <div class="cover">
        <img v-if="b.coverUrl" :src="b.coverUrl" :alt="b.title" />
        <div v-else class="placeholder">📚</div>
      </div>

      <div class="meta">
        <div class="top">
          <h3 class="title">{{ b.title }}</h3>
          <span class="pill">{{ BookCategoryLabels[b.category] }}</span>
        </div>

        <div class="muted">{{ b.author }} • {{ b.publicationYear }}</div>
        <p v-if="b.description" class="desc">{{ b.description }}</p>

        <div class="bottom">
          <span class="isbn muted">{{ b.isbn }}</span>
          <strong>{{ b.price.toFixed(2) }} €</strong>
        </div>
      </div>
    </article>

    <p v-if="booksStore.filteredBooks.length === 0" class="muted">
      Aucun résultat.
    </p>
  </div>
</template>

<style scoped>
.head {
  display: flex;
  align-items: end;
  justify-content: space-between;
  gap: 14px;
  margin-bottom: 14px;
  flex-wrap: wrap;
}
.filters {
  display: flex;
  gap: 10px;
  padding: 12px;
  align-items: center;
  flex-wrap: wrap;
}
.input {
  min-width: 240px;
  padding: 10px 12px;
  border-radius: 12px;
  border: 1px solid var(--border);
  background: rgba(255,255,255,0.04);
  color: var(--text);
  outline: none;
}
.grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}
@media (max-width: 900px) {
  .grid { grid-template-columns: 1fr; }
}
.book {
  display: grid;
  grid-template-columns: 110px 1fr;
  gap: 12px;
  padding: 12px;
}
.cover {
  width: 110px;
  height: 150px;
  border-radius: 14px;
  overflow: hidden;
  border: 1px solid var(--border);
  background: rgba(255,255,255,0.04);
  display: grid;
  place-items: center;
}
.cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.placeholder {
  font-size: 32px;
  opacity: .7;
}
.meta { display: flex; flex-direction: column; gap: 8px; }
.top { display: flex; gap: 10px; align-items: start; justify-content: space-between; }
.title { margin: 0; font-size: 1.05rem; line-height: 1.2; }
.pill {
  padding: 6px 10px;
  border-radius: 999px;
  border: 1px solid var(--border);
  background: rgba(255,255,255,0.05);
  color: var(--muted);
  font-size: .85rem;
  white-space: nowrap;
}
.desc {
  margin: 0;
  color: var(--muted);
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.bottom {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  gap: 10px;
  margin-top: auto;
}
.isbn { font-size: .85rem; }
.muted { color: var(--muted); }
</style>
