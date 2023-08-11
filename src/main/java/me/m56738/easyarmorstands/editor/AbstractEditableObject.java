package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.property.PropertyRegistry;

public abstract class AbstractEditableObject implements EditableObject {
    private final PropertyRegistry properties = new Registry(this);

    @Override
    public PropertyRegistry properties() {
        return properties;
    }

    private static class Registry extends PropertyRegistry {
        private final EditableObject owner;

        private Registry(EditableObject owner) {
            this.owner = owner;
        }

        @Override
        public boolean isValid() {
            return owner.isValid();
        }

        @Override
        public void commit() {
            // properties contained in this registry perform their changes immediately
        }
    }
}
