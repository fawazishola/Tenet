# Tenet Documentation

This directory contains the source files for the Tenet documentation website, built with [mdBook](https://rust-lang.github.io/mdBook/).

## Prerequisites

- [Rust](https://rustup.rs/) (for installing mdBook)

## Installation

```bash
cargo install mdbook
```

## Local Development

Build the book:
```bash
mdbook build
```

Serve locally with hot-reload:
```bash
mdbook serve --open
```

This opens the docs at `http://localhost:3000`.

## Structure

```
book/
├── book.toml          # Configuration
├── src/
│   ├── SUMMARY.md     # Table of contents
│   ├── introduction.md
│   ├── getting-started/
│   ├── language/
│   ├── game-theory/
│   ├── gallery/
│   ├── reference/
│   └── appendix/
└── theme/
    └── css/
        └── custom.css # Cyberpunk theme
```

## Deployment

The documentation is automatically deployed to GitHub Pages when changes are pushed to the `main` branch.

**Live site:** https://yourusername.github.io/tenet/

## Theme

The documentation uses a custom cyberpunk theme with:
- Neon cyan (`#00FFFF`) accents
- Deep black (`#0a0a0f`) background
- Glow effects on interactive elements
- JetBrains Mono font for code

## Contributing

To add or edit documentation:

1. Edit the Markdown files in `src/`
2. Update `SUMMARY.md` if adding new pages
3. Test locally with `mdbook serve`
4. Submit a PR
