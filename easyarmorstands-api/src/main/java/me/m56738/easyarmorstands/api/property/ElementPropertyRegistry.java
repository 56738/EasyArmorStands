package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.element.Element;

class ElementPropertyRegistry extends PropertyRegistry {
    private final Element element;

    ElementPropertyRegistry(Element element) {
        this.element = element;
    }

    @Override
    public boolean isValid() {
        return element.isValid();
    }
}
