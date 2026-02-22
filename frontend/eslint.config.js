import js from '@eslint/js';
import globals from 'globals';
import eslintPluginReactHooks from 'eslint-plugin-react-hooks';
import eslintPluginReactRefresh from 'eslint-plugin-react-refresh';
import eslintPluginPrettier from 'eslint-plugin-prettier';
import tseslint from 'typescript-eslint';
import { defineConfig, globalIgnores } from 'eslint/config';

export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{ts,tsx}'],
    extends: [
      js.configs.recommended,
      tseslint.configs.recommended,
      eslintPluginReactHooks.configs.flat.recommended,
      eslintPluginReactRefresh.configs.vite,
    ],
    plugins: {
      prettier: eslintPluginPrettier,
    },
    rules: {
      'prettier/prettier': ['error', { usePrettierrc: true }],
      'no-restricted-syntax': [
        'error',
        {
          selector: 'FunctionDeclaration:not([id.name=/^[A-Z]/])',
          message:
            'Use arrow functions (const fn = () => ...) for non-component functions. Function declarations are reserved for React components.',
        },
      ],
      'prefer-arrow-callback': ['error', { allowNamedFunctions: false }],
      'prefer-const': 'error',
      quotes: ['error', 'single', { avoidEscape: true }],
      'jsx-quotes': ['error', 'prefer-single'],
      '@typescript-eslint/no-unused-vars': ['warn', { argsIgnorePattern: '^_' }],
      'react/react-in-jsx-scope': 'off',
      'import/extensions': 'off',
    },
    languageOptions: {
      ecmaVersion: 2020,
      globals: globals.browser,
    },
  },
]);
