import { defineConfig } from 'vite';
import { viteSingleFile } from "vite-plugin-singlefile";

export default defineConfig({
  plugins: [
    viteSingleFile()
  ],
  build: {
    // Optional: ensures the output is as clean as possible
    target: "esnext",
    assetsInlineLimit: 100000000, // Inline everything
    chunkSizeWarningLimit: 100000000,
    cssCodeSplit: false,
  },
  server: {
    // This ensures Vite watches the dist-js folder for changes
    watch: {
      usePolling: true
    }
  }
});
