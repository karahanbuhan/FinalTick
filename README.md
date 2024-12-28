# FinalTick

A Spigot plugin for executing commands at specified intervals before a target time.

## Configuration

```yaml
# Target time in milliseconds since Unix Epoch (https://currentmillis.com/)
target: 1735340940000
triggers:
  100000000:
    - "say This will be called once and for sure, you can initialize scoreboards and stuff here"
  # This is triggered always as it is infinite (corresponds to 68 years, so should work for most cases)
  -100000000:
    - "say &3%countdown% seconds left!" # & character is used for color, and %countdown% for the countdown in seconds
  60:
    - "say &4Last %countdown% seconds!"
  30:
    - "say &4&bLAST %countdown% SECONDS!"
  -10:
    - "say &6%countdown%!"
    - "give @a minecraft:diamond"
  0:
    - "say &7OLEY!"
```

### Placeholders
- `%countdown%`: Remaining seconds
- `&`: Color codes

### Execution Logic
- Positive triggers: Run once when countdown matches exactly
- Negative triggers: Run repeatedly while countdown is below threshold
- Commands execute in console context
- Color codes are automatically translated

### Example Uses
- Server events countdown
- Timed rewards distribution
- Tournament start announcements
- Scheduled server restarts

## Installation
1. Download FinalTick.jar
2. Place in plugins folder
3. Start server to generate config
4. Edit config.yml
5. Reload server

Known Bugs

If server starts with remaining time less than trigger thresholds, all matching triggers execute immediately due to <= comparison
Example: Starting with 5 seconds left will trigger all thresholds ≥ 5 seconds

However this shouldn't be a major concern for events and such. Reloading the server when close to target time may cause inconsistent behaviour.
