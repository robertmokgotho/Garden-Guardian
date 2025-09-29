// Garden problem knowledge base
        const gardenKnowledge = {
            pests: {
                aphids: "Aphids can be controlled by spraying plants with a mixture of water and a few drops of dish soap. Ladybugs are natural predators that eat aphids.",
                slugs: "Use beer traps or copper tape around plants to deter slugs. Diatomaceous earth sprinkled around plants also works well.",
                caterpillars: "Handpick caterpillars off plants or use Bacillus thuringiensis (Bt), a natural bacteria that targets caterpillars.",
                general: "For pest problems, try companion planting with marigolds, garlic, or onions which naturally repel many pests. Neem oil is also an effective organic pesticide."
            },
            water: {
                overwatering: "Signs of overwatering include yellow leaves and root rot. Allow soil to dry between waterings and ensure proper drainage.",
                underwatering: "Wilting, dry soil, and brown leaf tips indicate underwatering. Water deeply but less frequently to encourage deep root growth.",
                irrigation: "Consider drip irrigation systems for efficient watering. Mulching helps retain soil moisture and reduces watering needs.",
                general: "Most plants need about 1 inch of water per week. Water in the morning to reduce evaporation and prevent fungal diseases."
            },
            space: {
                small: "Use vertical gardening with trellises, hanging baskets, or wall planters. Container gardening is great for small spaces.",
                companion: "Practice companion planting to maximize space. For example, plant tall corn with low-growing squash and beans (Three Sisters method).",
                containers: "Many vegetables grow well in containers. Use 5-gallon buckets for tomatoes, peppers need 3-5 gallon containers, and herbs can grow in smaller pots.",
                general: "Consider square foot gardening to maximize small spaces. Use tiered planters or raised beds to create more growing area."
            },
            cost: {
                seeds: "Save seeds from your best plants for next season. Join seed swap groups in your community.",
                fertilizer: "Make compost from kitchen scraps and yard waste. Use coffee grounds as fertilizer for acid-loving plants.",
                tools: "Look for used garden tools at thrift stores or online marketplaces. Share tools with gardening neighbors.",
                general: "Start plants from seeds instead of buying seedlings. Use recycled containers as planters. Make your own pest control solutions."
            }
        };

        // Quick response options
        const quickOptions = [
            "Pest problems",
            "Watering issues",
            "Limited space",
            "Budget gardening",
            "Soil quality",
            "Plant diseases"
        ];

        // DOM elements
        const chatbotToggle = document.getElementById('chatbotToggle');
        const chatbotWindow = document.getElementById('chatbotWindow');
        const chatbotClose = document.getElementById('chatbotClose');
        const chatbotMessages = document.getElementById('chatbotMessages');
        const chatbotInput = document.getElementById('chatbotInput');
        const chatbotSend = document.getElementById('chatbotSend');
        const quickOptionsContainer = document.getElementById('quickOptions');

        // Initialize chatbot
        function initChatbot() {
            // Add welcome message
            addBotMessage("Hello! I'm your garden assistant. How can I help with your garden problems today?");

            // Add quick options
            renderQuickOptions();

            // Event listeners
            chatbotToggle.addEventListener('click', toggleChatbot);
            chatbotClose.addEventListener('click', toggleChatbot);
            chatbotSend.addEventListener('click', sendMessage);
            chatbotInput.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    sendMessage();
                }
            });
        }

        // Toggle chatbot visibility
        function toggleChatbot() {
            if (chatbotWindow.style.display === 'flex') {
                chatbotWindow.style.display = 'none';
            } else {
                chatbotWindow.style.display = 'flex';
                chatbotInput.focus();
            }
        }

        // Add bot message
        function addBotMessage(text) {
            const messageDiv = document.createElement('div');
            messageDiv.className = 'message bot-message';
            messageDiv.textContent = text;
            chatbotMessages.appendChild(messageDiv);
            scrollToBottom();
        }

        // Add user message
        function addUserMessage(text) {
            const messageDiv = document.createElement('div');
            messageDiv.className = 'message user-message';
            messageDiv.textContent = text;
            chatbotMessages.appendChild(messageDiv);
            scrollToBottom();
        }

        // Show typing indicator
        function showTypingIndicator() {
            const typingDiv = document.createElement('div');
            typingDiv.className = 'typing-indicator';
            typingDiv.id = 'typingIndicator';
            typingDiv.innerHTML = `
                <div class="typing-dot"></div>
                <div class="typing-dot"></div>
                <div class="typing-dot"></div>
            `;
            chatbotMessages.appendChild(typingDiv);
            scrollToBottom();
        }

        // Hide typing indicator
        function hideTypingIndicator() {
            const typingIndicator = document.getElementById('typingIndicator');
            if (typingIndicator) {
                typingIndicator.remove();
            }
        }

        // Scroll to bottom of messages
        function scrollToBottom() {
            chatbotMessages.scrollTop = chatbotMessages.scrollHeight;
        }

        // Render quick options
        function renderQuickOptions() {
            quickOptionsContainer.innerHTML = '';
            quickOptions.forEach(option => {
                const optionElement = document.createElement('div');
                optionElement.className = 'quick-option';
                optionElement.textContent = option;
                optionElement.addEventListener('click', () => {
                    handleQuickOption(option);
                });
                quickOptionsContainer.appendChild(optionElement);
            });
        }

        // Handle quick option selection
        function handleQuickOption(option) {
            addUserMessage(option);
            showTypingIndicator();

            setTimeout(() => {
                hideTypingIndicator();
                let response = '';

                switch(option) {
                    case "Pest problems":
                        response = "I can help with pest issues! Common garden pests include aphids, slugs, and caterpillars. What specific pest are you dealing with?";
                        break;
                    case "Watering issues":
                        response = "Watering problems are common. Are you dealing with overwatering, underwatering, or irrigation system questions?";
                        break;
                    case "Limited space":
                        response = "Small garden spaces can be challenging but rewarding! I can suggest vertical gardening, container options, or space-efficient planting techniques.";
                        break;
                    case "Budget gardening":
                        response = "Gardening on a budget is possible! I have tips for saving on seeds, making your own fertilizer, and finding affordable tools.";
                        break;
                    case "Soil quality":
                        response = "Good soil is the foundation of a healthy garden. I can help with soil testing, amendments, and composting advice.";
                        break;
                    case "Plant diseases":
                        response = "Plant diseases can be frustrating. Tell me about the symptoms you're seeing and I'll suggest organic treatment options.";
                        break;
                    default:
                        response = "I'd be happy to help with that! Could you provide more details about your garden problem?";
                }

                addBotMessage(response);
            }, 1000);
        }

        // Process user message and generate response
        function processUserMessage(message) {
            const lowerMessage = message.toLowerCase();
            let response = "I'm here to help with garden problems! Could you provide more details about your specific issue with pests, water, space, or costs?";

            // Check for pest-related keywords
            if (lowerMessage.includes('pest') || lowerMessage.includes('bug') || lowerMessage.includes('insect')) {
                if (lowerMessage.includes('aphid')) {
                    response = gardenKnowledge.pests.aphids;
                } else if (lowerMessage.includes('slug')) {
                    response = gardenKnowledge.pests.slugs;
                } else if (lowerMessage.includes('caterpillar')) {
                    response = gardenKnowledge.pests.caterpillars;
                } else {
                    response = gardenKnowledge.pests.general;
                }
            }
            // Check for water-related keywords
            else if (lowerMessage.includes('water') || lowerMessage.includes('irrigation') || lowerMessage.includes('dry') || lowerMessage.includes('wet')) {
                if (lowerMessage.includes('overwater') || lowerMessage.includes('too much water')) {
                    response = gardenKnowledge.water.overwatering;
                } else if (lowerMessage.includes('underwater') || lowerMessage.includes('not enough water')) {
                    response = gardenKnowledge.water.underwatering;
                } else if (lowerMessage.includes('irrigation') || lowerMessage.includes('system')) {
                    response = gardenKnowledge.water.irrigation;
                } else {
                    response = gardenKnowledge.water.general;
                }
            }
            // Check for space-related keywords
            else if (lowerMessage.includes('space') || lowerMessage.includes('small') || lowerMessage.includes('container') || lowerMessage.includes('vertical')) {
                if (lowerMessage.includes('small') || lowerMessage.includes('limited')) {
                    response = gardenKnowledge.space.small;
                } else if (lowerMessage.includes('companion') || lowerMessage.includes('planting together')) {
                    response = gardenKnowledge.space.companion;
                } else if (lowerMessage.includes('container') || lowerMessage.includes('pot')) {
                    response = gardenKnowledge.space.containers;
                } else {
                    response = gardenKnowledge.space.general;
                }
            }
            // Check for cost-related keywords
            else if (lowerMessage.includes('cost') || lowerMessage.includes('budget') || lowerMessage.includes('money') || lowerMessage.includes('expensive') || lowerMessage.includes('cheap')) {
                if (lowerMessage.includes('seed')) {
                    response = gardenKnowledge.cost.seeds;
                } else if (lowerMessage.includes('fertilizer') || lowerMessage.includes('compost')) {
                    response = gardenKnowledge.cost.fertilizer;
                } else if (lowerMessage.includes('tool')) {
                    response = gardenKnowledge.cost.tools;
                } else {
                    response = gardenKnowledge.cost.general;
                }
            }

            return response;
        }

        // Send message
        function sendMessage() {
            const message = chatbotInput.value.trim();
            if (message === '') return;

            addUserMessage(message);
            chatbotInput.value = '';

            showTypingIndicator();

            setTimeout(() => {
                hideTypingIndicator();
                const response = processUserMessage(message);
                addBotMessage(response);
            }, 1000);
        }

        // Initialize chatbot when page loads
        document.addEventListener('DOMContentLoaded', initChatbot);