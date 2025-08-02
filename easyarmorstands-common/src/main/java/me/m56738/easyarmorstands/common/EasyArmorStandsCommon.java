package me.m56738.easyarmorstands.common;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.context.ChangeContextFactory;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.common.clipboard.Clipboard;
import me.m56738.easyarmorstands.common.clipboard.ClipboardManager;
import me.m56738.easyarmorstands.common.command.PropertyCommands;
import me.m56738.easyarmorstands.common.command.parser.BlockDataParser;
import me.m56738.easyarmorstands.common.command.parser.EntityParser;
import me.m56738.easyarmorstands.common.command.parser.EntitySelectorParser;
import me.m56738.easyarmorstands.common.command.parser.LocationParser;
import me.m56738.easyarmorstands.common.command.parser.NodeValueArgumentParser;
import me.m56738.easyarmorstands.common.command.processor.ClipboardInjector;
import me.m56738.easyarmorstands.common.command.processor.ClipboardProcessor;
import me.m56738.easyarmorstands.common.command.processor.ElementInjector;
import me.m56738.easyarmorstands.common.command.processor.ElementProcessor;
import me.m56738.easyarmorstands.common.command.processor.ElementSelectionInjector;
import me.m56738.easyarmorstands.common.command.processor.ElementSelectionProcessor;
import me.m56738.easyarmorstands.common.command.processor.GroupProcessor;
import me.m56738.easyarmorstands.common.command.processor.SessionInjector;
import me.m56738.easyarmorstands.common.command.processor.SessionProcessor;
import me.m56738.easyarmorstands.common.command.processor.ValueNodeInjector;
import me.m56738.easyarmorstands.common.command.requirement.CommandRequirementBuilderModifier;
import me.m56738.easyarmorstands.common.command.requirement.CommandRequirementPostProcessor;
import me.m56738.easyarmorstands.common.command.requirement.ElementRequirement;
import me.m56738.easyarmorstands.common.command.requirement.ElementSelectionRequirement;
import me.m56738.easyarmorstands.common.command.requirement.RequireElement;
import me.m56738.easyarmorstands.common.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.common.command.requirement.RequireSession;
import me.m56738.easyarmorstands.common.command.requirement.SessionRequirement;
import me.m56738.easyarmorstands.common.command.util.ElementSelection;
import me.m56738.easyarmorstands.common.editor.CommonSessionManager;
import me.m56738.easyarmorstands.common.editor.SessionImpl;
import me.m56738.easyarmorstands.common.editor.SessionListener;
import me.m56738.easyarmorstands.common.editor.node.ValueNode;
import me.m56738.easyarmorstands.common.element.ArmorStandElementProvider;
import me.m56738.easyarmorstands.common.element.ArmorStandElementType;
import me.m56738.easyarmorstands.common.element.CommonEntityElementProviderRegistry;
import me.m56738.easyarmorstands.common.element.DisplayElementProvider;
import me.m56738.easyarmorstands.common.element.DisplayElementType;
import me.m56738.easyarmorstands.common.element.ElementSpawnRequestImpl;
import me.m56738.easyarmorstands.common.element.InteractionElementProvider;
import me.m56738.easyarmorstands.common.element.InteractionElementType;
import me.m56738.easyarmorstands.common.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.common.element.TextDisplayElementType;
import me.m56738.easyarmorstands.common.history.HistoryManager;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.property.context.PlayerChangeContextFactory;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.component.DefaultValue;
import org.incendo.cloud.exception.InvalidCommandSenderException;
import org.incendo.cloud.help.result.CommandEntry;
import org.incendo.cloud.injection.ParameterInjectorRegistry;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.minecraft.extras.parser.TextColorParser;
import org.incendo.cloud.parser.ParserRegistry;
import org.incendo.cloud.parser.standard.StringParser;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;

import static me.m56738.easyarmorstands.common.command.processor.ObjectInjector.injector;

public class EasyArmorStandsCommon implements EasyArmorStands {
    private final CommonPlatform platform;
    private final String version;
    private final HistoryManager historyManager;
    private final ClipboardManager clipboardManager;
    private final CommonEntityElementProviderRegistry entityElementProviderRegistry;
    private final CommonSessionManager sessionManager;
    private final SessionListener sessionListener;
    private final ChangeContextFactory changeContextFactory;

    private final ArmorStandElementType armorStandType;
    private final DisplayElementType itemDisplayType;
    private final DisplayElementType blockDisplayType;
    private final TextDisplayElementType textDisplayType;
    private final InteractionElementType interactionType;

    public EasyArmorStandsCommon(CommonPlatform platform, String version) {
        this.platform = platform;
        this.version = version;
        this.historyManager = new HistoryManager(platform);
        this.clipboardManager = new ClipboardManager(this);
        this.entityElementProviderRegistry = new CommonEntityElementProviderRegistry(platform);
        this.sessionManager = new CommonSessionManager(this);
        this.sessionListener = new SessionListener(this, sessionManager);
        this.changeContextFactory = new PlayerChangeContextFactory(historyManager);

        this.armorStandType = new ArmorStandElementType(this);
        this.itemDisplayType = new DisplayElementType(this, platform.getItemDisplayType());
        this.blockDisplayType = new DisplayElementType(this, platform.getBlockDisplayType());
        this.textDisplayType = new TextDisplayElementType(this);
        this.interactionType = new InteractionElementType(this);

        this.entityElementProviderRegistry.register(new ArmorStandElementProvider(armorStandType));
        this.entityElementProviderRegistry.register(new SimpleEntityElementProvider(this));
        this.entityElementProviderRegistry.register(new DisplayElementProvider(itemDisplayType));
        this.entityElementProviderRegistry.register(new DisplayElementProvider(blockDisplayType));
        this.entityElementProviderRegistry.register(new DisplayElementProvider(textDisplayType));
        this.entityElementProviderRegistry.register(new InteractionElementProvider(interactionType));
    }

    public static void registerCommands(CommandManager<CommandSource> commandManager, EasyArmorStandsCommonProvider provider) {
        commandManager.registerCommandPreProcessor(new ElementSelectionProcessor());
        commandManager.registerCommandPreProcessor(new GroupProcessor());
        commandManager.registerCommandPreProcessor(new ElementProcessor());
        commandManager.registerCommandPreProcessor(new SessionProcessor());
        commandManager.registerCommandPreProcessor(new ClipboardProcessor());
        commandManager.registerCommandPostProcessor(new CommandRequirementPostProcessor());

        registerParameterInjection(commandManager.parameterInjectorRegistry(), provider);
        registerParsers(commandManager.parserRegistry());

        MinecraftHelp<CommandSource> help = MinecraftHelp.create("/eas help", commandManager, CommandSource::source);
        commandManager.command(commandManager.commandBuilder("eas")
                .literal("help")
                .optional("query", StringParser.greedyStringParser(), DefaultValue.constant(""),
                        SuggestionProvider.blocking((context, input) ->
                                commandManager.createHelpHandler()
                                        .queryRootIndex(context.sender())
                                        .entries()
                                        .stream()
                                        .map(CommandEntry::syntax)
                                        .map(Suggestion::suggestion)
                                        .toList()))
                .commandDescription(RichDescription.translatable("easyarmorstands.command.description.help"))
                .permission(Permissions.HELP)
                .handler(context -> help.queryCommands(context.get("query"), context.sender())));

        registerExceptionHandler(commandManager);

        AnnotationParser<CommandSource> annotationParser = createAnnotationParser(commandManager);
        try {
            annotationParser.parseContainers(EasyArmorStandsCommon.class.getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        PropertyCommands.register(commandManager);
    }

    private static void registerParsers(ParserRegistry<CommandSource> parserRegistry) {
        parserRegistry
                .registerNamedParserSupplier("node_value", p -> new NodeValueArgumentParser<>())
                .registerParser(TextColorParser.textColorParser())
                .registerParser(BlockDataParser.blockDataParser())
                .registerParser(LocationParser.locationParser())
                .registerParser(EntitySelectorParser.entitySelectorParser())
                .registerParser(EntityParser.entityParser());
    }

    private static void registerParameterInjection(ParameterInjectorRegistry<CommandSource> parameterInjectorRegistry, EasyArmorStandsCommonProvider provider) {
        parameterInjectorRegistry
                .registerInjector(EasyArmorStands.class, injector(provider::getEasyArmorStands))
                .registerInjector(EasyArmorStandsCommon.class, injector(provider::getEasyArmorStands))
                .registerInjector(HistoryManager.class, injector(() -> provider.getEasyArmorStands().historyManager()))
                .registerInjector(SessionManager.class, injector(() -> provider.getEasyArmorStands().sessionManager()))
                .registerInjector(SessionListener.class, injector(() -> provider.getEasyArmorStands().sessionListener()))
                .registerInjector(ClipboardManager.class, injector(() -> provider.getEasyArmorStands().clipboardManager()))
                .registerInjector(Platform.class, injector(() -> provider.getEasyArmorStands().platform()))
                .registerInjector(ValueNode.class, new ValueNodeInjector())
                .registerInjector(SessionImpl.class, new SessionInjector())
                .registerInjector(Clipboard.class, new ClipboardInjector())
                .registerInjector(Element.class, new ElementInjector())
                .registerInjector(ElementSelection.class, new ElementSelectionInjector());
    }

    private static AnnotationParser<CommandSource> createAnnotationParser(CommandManager<CommandSource> commandManager) {
        AnnotationParser<CommandSource> annotationParser = new AnnotationParser<>(commandManager, CommandSource.class);
        annotationParser.descriptionMapper(RichDescription::translatable);
        annotationParser.registerBuilderModifier(RequireSession.class, new CommandRequirementBuilderModifier<>(a -> new SessionRequirement()));
        annotationParser.registerBuilderModifier(RequireElement.class, new CommandRequirementBuilderModifier<>(a -> new ElementRequirement()));
        annotationParser.registerBuilderModifier(RequireElementSelection.class, new CommandRequirementBuilderModifier<>(a -> new ElementSelectionRequirement()));
        return annotationParser;
    }

    private static void registerExceptionHandler(CommandManager<CommandSource> commandManager) {
        MinecraftExceptionHandler.create(CommandSource::source)
                .defaultArgumentParsingHandler()
                .defaultInvalidSyntaxHandler()
                .defaultNoPermissionHandler()
                .defaultCommandExecutionHandler()
                .defaultInvalidSenderHandler()
                .handler(InvalidCommandSenderException.class,
                        (sender, e) -> Message.error("easyarmorstands.error.not-a-player"))
                .registerTo(commandManager);
    }

    @Override
    public CommonPlatform platform() {
        return platform;
    }

    public String version() {
        return version;
    }

    @Override
    public CommonEntityElementProviderRegistry entityElementProviderRegistry() {
        return entityElementProviderRegistry;
    }

    public HistoryManager historyManager() {
        return historyManager;
    }

    public ClipboardManager clipboardManager() {
        return clipboardManager;
    }

    @Override
    public CommonSessionManager sessionManager() {
        return sessionManager;
    }

    public SessionListener sessionListener() {
        return sessionListener;
    }

    @Override
    public ElementSpawnRequest elementSpawnRequest(ElementType type) {
        return new ElementSpawnRequestImpl(this, type);
    }

    @Override
    public ChangeContextFactory changeContext() {
        return changeContextFactory;
    }

    public ElementType getItemDisplayType() {
        return itemDisplayType;
    }

    public void close() {
        sessionManager.stopAllSessions();
        platform.close();
    }
}
