// Client-side validation and dynamic preview
const form = document.getElementById('calculatorForm');
const calculateBtn = document.getElementById('calculateBtn');
const savePlanBtn = document.getElementById('savePlan');
const cropsContainer = document.getElementById('cropsContainer');

/*<![CDATA[*/
let cropCount = /*[[${input.crops != null and input.crops.size() > 0}]]*/false ? /*[[${input.crops.size()}]]*/1 : 1;
/*]]>*/

function validateForm() {
    const inputs = form.querySelectorAll('input[type="number"]');
    let valid = true;
    inputs.forEach(input => {
        if (input.value < 0 || (input.required && !input.value)) {
            valid = false;
        }
    });
    calculateBtn.disabled = !valid;
    savePlanBtn.disabled = !valid;
}

form.querySelectorAll('input').forEach(input => {
    input.addEventListener('input', validateForm);
});

// Add crop dynamically
document.getElementById('addCrop').addEventListener('click', () => {
    cropCount++;
    const div = document.createElement('div');
    div.className = 'crop-container';
    div.innerHTML = `
        <div class="crop-header">Crop ${cropCount}</div>
        <label for="crops[${cropCount - 1}].name">Crop Name (optional):</label>
        <input type="text" name="crops[${cropCount - 1}].name" id="crops[${cropCount - 1}].name" />
        <label for="crops[${cropCount - 1}].plantSpacing">Plant Spacing:</label>
        <input type="number" name="crops[${cropCount - 1}].plantSpacing" id="crops[${cropCount - 1}].plantSpacing" step="0.01" required />
        <select name="crops[${cropCount - 1}].spacingUnit" id="crops[${cropCount - 1}].spacingUnit">
            <option value="meters">Meters</option>
            <option value="centimeters">Centimeters</option>
        </select>
        <label for="crops[${cropCount - 1}].seedsPerPack">Seeds per Pack:</label>
        <input type="number" name="crops[${cropCount - 1}].seedsPerPack" id="crops[${cropCount - 1}].seedsPerPack" required />
        <label for="crops[${cropCount - 1}].pricePerPack">Price per Seed Pack (ZAR):</label>
        <input type="number" name="crops[${cropCount - 1}].pricePerPack" id="crops[${cropCount - 1}].pricePerPack" step="0.01" required />
    `;
    cropsContainer.appendChild(div);
    div.querySelectorAll('input').forEach(input => input.addEventListener('input', validateForm));
    validateForm();
});

// Save plan to session storage
document.getElementById('savePlan').addEventListener('click', () => {
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        if (key.includes('[')) {
            const match = key.match(/(\w+)\[(\d+)\]\.(\w+)/);
            if (match) {
                const [, base, index, field] = match;
                if (!data[base]) data[base] = [];
                if (!data[base][index]) data[base][index] = {};
                data[base][index][field] = value;
            }
        } else {
            data[key] = value;
        }
    });
    sessionStorage.setItem('gardenPlan', JSON.stringify(data));
});

// Enhanced canvas drawing function
function drawGardenLayout() {
    const canvas = document.getElementById('gardenCanvas');
    if (!canvas) return;

    try {
        const ctx = canvas.getContext('2d');

        // Get form values
        const gardenLengthInput = parseFloat(document.getElementById('gardenLength')?.value) || 0;
        const gardenWidthInput = parseFloat(document.getElementById('gardenWidth')?.value) || 0;
        const borderWidthInput = parseFloat(document.getElementById('borderWidth')?.value) || 0;
        const pathwayWidthInput = parseFloat(document.getElementById('pathwayWidth')?.value) || 0;
        const pathwayLengthInput = parseFloat(document.getElementById('pathwayLength')?.value) || 0;

        const lengthUnit = document.getElementById('lengthUnit')?.value || 'meters';
        const widthUnit = document.getElementById('widthUnit')?.value || 'meters';
        const borderUnit = document.getElementById('borderUnit')?.value || 'meters';
        const pathwayUnit = document.getElementById('pathwayUnit')?.value || 'meters';
        const layoutType = document.getElementById('layoutType')?.value || 'grid';

        // Convert all to meters
        const gardenLength = lengthUnit === 'centimeters' ? gardenLengthInput / 100 : gardenLengthInput;
        const gardenWidth = widthUnit === 'centimeters' ? gardenWidthInput / 100 : gardenWidthInput;
        const borderWidth = borderUnit === 'centimeters' ? borderWidthInput / 100 : borderWidthInput;
        const pathwayWidth = pathwayUnit === 'centimeters' ? pathwayWidthInput / 100 : pathwayWidthInput;
        const pathwayLength = pathwayUnit === 'centimeters' ? pathwayLengthInput / 100 : pathwayLengthInput;

        // Validate inputs
        if (gardenLength <= 0 || gardenWidth <= 0) {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.fillStyle = '#999';
            ctx.font = '14px Arial';
            ctx.fillText('Enter valid garden dimensions', 10, 20);
            return;
        }

        // Calculate effective area
        const effectiveLength = gardenLength - 2 * borderWidth - pathwayLength;
        const effectiveWidth = gardenWidth - 2 * borderWidth - pathwayWidth;

        if (effectiveLength <= 0 || effectiveWidth <= 0) {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.fillStyle = '#999';
            ctx.font = '14px Arial';
            ctx.fillText('Effective area too small', 10, 20);
            return;
        }

        // Extract all crop data from form inputs (not from results DOM)
        const crops = [];
        let cropIndex = 0;

        while (true) {
            const nameInput = document.querySelector(`input[name="crops[${cropIndex}].name"]`);
            const spacingInput = document.querySelector(`input[name="crops[${cropIndex}].plantSpacing"]`);
            const spacingUnitSelect = document.querySelector(`select[name="crops[${cropIndex}].spacingUnit"]`);

            if (!nameInput || !spacingInput) break;

            let spacing = parseFloat(spacingInput.value) || 0.1;
            const spacingUnit = spacingUnitSelect?.value || 'meters';

            if (spacingUnit === 'centimeters') {
                spacing = spacing / 100;
            }

            // Calculate plants based on layout type
            let plantsAlongLength, plantsAlongWidth;
            if (layoutType === 'triangular') {
                plantsAlongLength = Math.floor(effectiveLength / spacing) + 1;
                plantsAlongWidth = Math.floor(effectiveWidth / (spacing * Math.sqrt(3) / 2)) + 1;
            } else {
                plantsAlongLength = Math.floor(effectiveLength / spacing) + 1;
                plantsAlongWidth = Math.floor(effectiveWidth / spacing) + 1;
            }

            const totalPlants = plantsAlongLength * plantsAlongWidth;

            crops.push({
                name: nameInput.value || `Crop ${cropIndex + 1}`,
                spacing: spacing,
                plants: totalPlants
            });

            cropIndex++;
        }

        if (crops.length === 0) return;

        // Set canvas dimensions
        const PADDING = 40;
        const MAX_CANVAS = 700;
        const scale = Math.min((MAX_CANVAS - 2 * PADDING) / gardenLength,
                              (MAX_CANVAS - 2 * PADDING) / gardenWidth);

        const canvasWidth = gardenLength * scale + 2 * PADDING;
        const canvasHeight = gardenWidth * scale + 2 * PADDING + 100; // Extra space for legend

        canvas.width = canvasWidth;
        canvas.height = canvasHeight;

        // Clear canvas with white background
        ctx.fillStyle = '#ffffff';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        const offsetX = PADDING;
        const offsetY = PADDING;

        // Draw border (if any)
        if (borderWidth > 0) {
            ctx.fillStyle = '#8B7355';
            ctx.fillRect(offsetX, offsetY, gardenLength * scale, gardenWidth * scale);
        }

        // Draw effective garden area
        const effectiveX = offsetX + borderWidth * scale;
        const effectiveY = offsetY + borderWidth * scale;
        const effectiveDrawLength = effectiveLength * scale;
        const effectiveDrawWidth = effectiveWidth * scale;

        ctx.fillStyle = '#E8F5E9';
        ctx.fillRect(effectiveX, effectiveY, effectiveDrawLength, effectiveDrawWidth);
        ctx.strokeStyle = '#4CAF50';
        ctx.lineWidth = 2;
        ctx.strokeRect(effectiveX, effectiveY, effectiveDrawLength, effectiveDrawWidth);

        // Draw pathways
        ctx.fillStyle = '#D4D4D4';
        if (pathwayWidth > 0) {
            const pathY = effectiveY + (effectiveDrawWidth - pathwayWidth * scale) / 2;
            ctx.fillRect(effectiveX, pathY, effectiveDrawLength, pathwayWidth * scale);
        }
        if (pathwayLength > 0) {
            const pathX = effectiveX + (effectiveDrawLength - pathwayLength * scale) / 2;
            ctx.fillRect(pathX, effectiveY, pathwayLength * scale, effectiveDrawWidth);
        }

        // Color palette for crops
        const colors = ['#FF6B6B', '#4ECDC4', '#45B7D1', '#FFA07A', '#98D8C8', '#F7DC6F'];

        // Draw plants for each crop
        crops.forEach((crop, cropIndex) => {
            if (crop.plants <= 0) return;

            const color = colors[cropIndex % colors.length];
            ctx.fillStyle = color;

            const spacingScaled = crop.spacing * scale;
            let plantCount = 0;

            for (let i = 0; i * crop.spacing <= effectiveLength && plantCount < crop.plants; i++) {
                for (let j = 0; j * crop.spacing <= effectiveWidth && plantCount < crop.plants; j++) {
                    let x = effectiveX + i * spacingScaled;
                    let y = effectiveY + j * spacingScaled;

                    // Apply triangular offset
                    if (layoutType === 'triangular' && j % 2 === 1) {
                        x += spacingScaled / 2;
                    }

                    // Check if within bounds
                    if (x < effectiveX || x > effectiveX + effectiveDrawLength ||
                        y < effectiveY || y > effectiveY + effectiveDrawWidth) {
                        continue;
                    }

                    // Skip if on pathway
                    if (pathwayWidth > 0) {
                        const pathY = effectiveY + (effectiveDrawWidth - pathwayWidth * scale) / 2;
                        if (Math.abs(y - pathY) < pathwayWidth * scale / 2) continue;
                    }
                    if (pathwayLength > 0) {
                        const pathX = effectiveX + (effectiveDrawLength - pathwayLength * scale) / 2;
                        if (Math.abs(x - pathX) < pathwayLength * scale / 2) continue;
                    }

                    // Draw plant
                    ctx.beginPath();
                    ctx.arc(x, y, 4, 0, 2 * Math.PI);
                    ctx.fill();
                    plantCount++;
                }
            }
        });

        // Draw legend with improved styling
        const legendStartY = offsetY + effectiveDrawWidth + 30;
        const legendX = offsetX + 20;

        ctx.font = 'bold 14px Arial';
        ctx.fillStyle = '#333333';
        ctx.fillText('Legend:', legendX, legendStartY);

        crops.forEach((crop, index) => {
            const color = colors[index % colors.length];
            const yPos = legendStartY + 25 + index * 25;

            // Draw color circle
            ctx.fillStyle = color;
            ctx.beginPath();
            ctx.arc(legendX + 15, yPos - 5, 7, 0, 2 * Math.PI);
            ctx.fill();

            // Draw text
            ctx.fillStyle = '#333333';
            ctx.font = '13px Arial';
            ctx.fillText(`${crop.name} (${crop.plants} plants)`, legendX + 35, yPos);
        });

    } catch (error) {
        console.error('Canvas drawing error:', error);
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    drawGardenLayout();
    validateForm();

    // Redraw on form changes
    form.querySelectorAll('input, select').forEach(element => {
        element.addEventListener('change', drawGardenLayout);
        element.addEventListener('input', drawGardenLayout);
    });
});